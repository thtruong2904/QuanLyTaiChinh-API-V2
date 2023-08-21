package com.tranhuutruong.finance.build.services;

import com.google.api.client.util.DateTime;
import com.tranhuutruong.finance.build.entities.users.Account;
import com.tranhuutruong.finance.build.entities.users.Role;
import com.tranhuutruong.finance.build.entities.users.Token;
import com.tranhuutruong.finance.build.entities.users.UserInformation;
import com.tranhuutruong.finance.build.repositories.User.AccountRepository;
import com.tranhuutruong.finance.build.repositories.User.RoleRepository;
import com.tranhuutruong.finance.build.repositories.User.TokenRepository;
import com.tranhuutruong.finance.build.repositories.User.UserRepository;
import com.tranhuutruong.finance.build.requests.User.*;
import com.tranhuutruong.finance.build.responses.ApiResponse;
import com.tranhuutruong.finance.build.responses.LoginResponse;
import com.tranhuutruong.finance.build.responses.UserRegisterResponse;
import com.tranhuutruong.finance.build.security.JWT.JwtProvider;
import com.tranhuutruong.finance.build.utils.CurrentDateTime;
import com.tranhuutruong.finance.build.utils.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final long expireInRefresh = Duration.ofHours(10).toMillis();
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private EmailService emailService;

    @Override
    public UserRegisterResponse register(UserRegisterRequest registerRequest)
    {
        Account accountModel = accountRepository.findAccountByUsername(registerRequest.getUserName());
        if(accountModel != null && accountModel.getId() >= 0)
        {
            return UserRegisterResponse.builder().status(false).message("Tài khoản đã tồn tại. Vui lòng nhập lại!").build();
        }
        if(accountRepository.existsByEmail(registerRequest.getEmail()))
        {
            return UserRegisterResponse.builder().status(false).message("Email đã tồn tại. Vui lòng nhập lại!").build();
        }
        Role roleModel = roleRepository.findByName(registerRequest.getRoleName());
        if(roleModel == null)
        {
            return UserRegisterResponse.builder().status(false).message("Role không tồn tại. Vui lòng kiểm tra lại!").build();
        }

        String hashedPassword = passwordEncoder.encode(registerRequest.getPassWord());
        accountModel = Account.builder().username(registerRequest.getUserName()).roleModel(roleModel).email(registerRequest.getEmail()).password(hashedPassword).isActivity(true).verifyCode("").build();
        UserInformation userModel = UserInformation.builder()
                .date(CurrentDateTime.getCurrentDateTime()).firstname(registerRequest.getFirstname())
                .lastname(registerRequest.getLastname())
                .accountModel(accountModel)
                .build();
        accountRepository.save(accountModel);
        userRepository.save(userModel);
        return UserRegisterResponse.builder().status(true).message("Tạo tài khoản thành công!").userModel(userModel).build();
    }

    // build token
    private LoginResponse buildToken(UserInformation userModel)
    {
        String jti = UUID.randomUUID().toString();
        tokenRepository.save(Token.builder().userInformation(userModel).tokenId(jti).expiredTime(System.currentTimeMillis() + expireInRefresh).build());
        return LoginResponse.builder().userModel(userModel).accessToken(jwtProvider.createToken(userModel)).expiresIn(System.currentTimeMillis() + expireInRefresh).refreshToken(jti).status(true).build();
//        return LoginResponse.builder().userModel(userModel).accessToken(jwtProvider.createToken(userModel)).status(true).build();
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest)
    {
        Account accountModel = accountRepository.findAccountByUsername(loginRequest.getUsername());
        if(accountModel == null || accountModel.getId() == null)
        {
            return LoginResponse.builder().message("Tài khoản không hợp lệ!").status(false).build();
        }
        else if(accountModel.isActivity() == false)
        {
            return LoginResponse.builder().message("Tài khoản đã bị khóa!").status(false).build();
        }
        else if(passwordEncoder.matches(loginRequest.getPassword(), accountModel.getPassword()))
        {
            return buildToken(userRepository.findUserInfoModelByAccountModel(accountModel));
        }
        else {
            return LoginResponse.builder().message("Tài khoản hoặc mật khẩu không hợp lệ. Vui lòng thử lại").status(false).build();
        }
    }

    @Override
    public LoginResponse refreshToken(String refreshToken)
    {
        Token tokenModel = tokenRepository.findByTokenId(refreshToken);
        if(tokenModel == null || tokenModel.getId() <= 0)
        {
            return LoginResponse.builder().message("Refresh token is not exists").status(false).build();
        }
        else {
            if(System.currentTimeMillis() > tokenModel.getExpiredTime())
            {
                return LoginResponse.builder().message("Jwt refresh token expired at " + new DateTime(tokenModel.getExpiredTime())).status(false).build();
            }
            Optional<UserInformation> userModel = userRepository.findById(tokenModel.getUserInformation().getId());
            return buildToken(userModel.get());
        }
    }

    @Override
    public ApiResponse<Object> changePassword(String username, ChangePasswordRequest changePasswordRequest)
    {
        Account account = accountRepository.findAccountByUsername(username);
        if(passwordEncoder.matches(changePasswordRequest.getPassword(), account.getPassword()) == false)
        {
            return ApiResponse.builder().status(101).message("Nhập sai mật khẩu cũ").build();
        }
        String hashedPassword = passwordEncoder.encode(changePasswordRequest.getNewPassword());
        account.setPassword(hashedPassword);
        accountRepository.save(account);
        return ApiResponse.builder().status(200).message("Thay đổi mật khẩu thành công").build();
    }

    @Override
    public ApiResponse<Object> forgotPassword(ForgotPasswordRequest forgotPasswordRequest)
    {
        Account account = accountRepository.findAccountByEmail(forgotPasswordRequest.getEmailRequest());
        if(account == null || account.getId() <= 0)
        {
            return ApiResponse.builder().status(101).message("Email không tồn tại trong hệ thống!").build();
        }
        else
        {
            UserInformation userInformation = userRepository.findUserInfoModelByAccountModel(account);
            String verifyCode = RandomUtils.getRandomVerifyCode();
            try {
                MimeMessage mailMessage = javaMailSender.createMimeMessage();
                MimeMessageHelper mailHelper = new MimeMessageHelper(mailMessage, true, "UTF-8");
                mailHelper.setFrom("tranhuutruong290401@gmail.com");
                mailHelper.setTo(forgotPasswordRequest.getEmailRequest());
                mailHelper.setSubject("Mã xác nhận lấy lại mật khẩu");
                mailHelper.setText("<html><body><p>Xin chào! " + userInformation.getLastname()
                        + "</p><p>Bạn đã yêu cầu cập nhật lại mật khẩu</p>"
                        + "<p>Đây là mã xác nhận lấy lại mật khẩu của bạn: </p>" + "<h2><b>" + verifyCode + "</b></h2>"
                        + "</body></html>" , true);
                emailService.sendEmail(mailMessage);
                account.setVerifyCode(verifyCode);
                accountRepository.save(account);
            } catch (Exception e)
                {
                    return ApiResponse.builder().status(101).message("Lỗi gửi verify code!").build();
                }
        }
        return ApiResponse.builder().status(200).message("Mã xác nhận đã được gửi qua email!").build();
    }

    @Override
    public ApiResponse<Object> checkVerifyCode(VerifyCodeCheckRequest verifyCode)
    {
        Account account = accountRepository.findAccountByEmail(verifyCode.getEmailRequest());
        if(account == null || account.getId() <= 0)
        {
            return ApiResponse.builder().status(400).message("Không tìm thấy tài khoản").build();
        }
        if(!account.getVerifyCode().equals(verifyCode.getCode()))
        {
            return ApiResponse.builder().status(101).message("Sai mã xác thực!").build();
        }
        return ApiResponse.builder().status(200).message("Xác nhận thành công!").build();
    }

    @Override
    public ApiResponse<Object> resetPassword(ResetPasswordRequest resetPasswordRequest)
    {
        Account account = accountRepository.findAccountByEmail(resetPasswordRequest.getEmail());
        if(account == null || account.getId() <= 0)
        {
            return ApiResponse.builder().status(400).message("Không tìm thấy tài khoản").build();
        }
        String hashedPassword = passwordEncoder.encode(resetPasswordRequest.getNewPassword());
        account.setPassword(hashedPassword);
        accountRepository.save(account);
        return ApiResponse.builder().status(200).message("Lấy lại mật khẩu thành công").build();
    }

    @Override
    public Iterable<UserInformation> getAllUser()
    {
        return userRepository.findAllByRoleName("USER");
    }

    @Override
    public ApiResponse<Object> getProfile(Long idUser)
    {
        Optional<UserInformation> userInformation = userRepository.findById(idUser);
        if(!userInformation.isPresent())
        {
            return ApiResponse.builder().status(404).message("Không tìm thấy tài khoản").build();
        }
        return ApiResponse.builder().status(200).message("Thông tin người dùng").data(userInformation.get()).build();
    }

    @Override
    public ApiResponse<Object> updateProfile(Long idUser, UserUpdateRequest userUpdateRequest)
    {
        Optional<UserInformation> userInformation = userRepository.findById(idUser);
        if(!userInformation.isPresent())
        {
            return ApiResponse.builder().status(404).message("Không tìm thấy tài khoản").build();
        }
        userInformation.get().setFirstname(userUpdateRequest.getFirstname());
        userInformation.get().setLastname(userUpdateRequest.getLastname());
        userRepository.save(userInformation.get());
        return ApiResponse.builder().status(200).message("Thay đổi thông tin thành công").build();
    }

    @Override
    public ApiResponse<Object> deleteUser(Long idUser)
    {
        Optional<UserInformation> userInformation = userRepository.findById(idUser);
        if(userInformation.get() == null)
        {
            return ApiResponse.builder().status(404).message("Không tim thấy người dùng").build();
        }
        userInformation.get().getAccountModel().setActivity(false);
        userRepository.save(userInformation.get());
        return ApiResponse.builder().status(200).message("Khóa người dùng thành công").build();
    }
    
    @Override
    public ApiResponse<Object> unlockUser(Long idUser)
    {
        Optional<UserInformation> userInformation = userRepository.findById(idUser);
        if(userInformation.get() == null)
        {
            return ApiResponse.builder().status(404).message("Không tim thấy người dùng").build();
        }
        userInformation.get().getAccountModel().setActivity(true);
        userRepository.save(userInformation.get());
        return ApiResponse.builder().status(200).message("Mở khóa người dùng thành công").build();
    }
}
