package com.tranhuutruong.finance.build.security.UserPrinciple;

import com.tranhuutruong.finance.build.entities.users.Account;
import com.tranhuutruong.finance.build.entities.users.UserInformation;
import com.tranhuutruong.finance.build.repositories.User.AccountRepository;
import com.tranhuutruong.finance.build.repositories.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
// interface UserDetailsService trong Spring Security
// được sử dụng để cung cấp thông tin chi tiết về người dùng từ một nguồn dữ liệu như cơ sở dữ liệu
public class UserDetailService implements UserDetailsService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UserRepository userRepository;

    // phương thức này được sử dụng để tìm kiếm thông tin người dùng trong nguồn dữ liệu
    // và tạo đối tượng UserDetails tương ứng
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account accountModel = accountRepository.findAccountByUsername(username);
        if(accountModel == null || accountModel.getId() <= 0)
        {
            throw new UsernameNotFoundException("Không tìm thấy tài khoản" + username);
        }
        UserInformation userModel = userRepository.findUserInfoModelByAccountModel(accountModel);
        return UserPrinciple.build(userModel);
    }
}
