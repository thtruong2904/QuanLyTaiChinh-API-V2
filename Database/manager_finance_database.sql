USE [master]
GO
/****** Object:  Database [MANAGER_FINANCE]    Script Date: 8/14/2023 11:56:55 PM ******/
CREATE DATABASE [MANAGER_FINANCE]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'MANAGER_FINANCE2', FILENAME = N'D:\SQL Server\MSSQL12.MSSQLSERVER\MSSQL\DATA\MANAGER_FINANCE2.mdf' , SIZE = 4096KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'MANAGER_FINANCE2_log', FILENAME = N'D:\SQL Server\MSSQL12.MSSQLSERVER\MSSQL\DATA\MANAGER_FINANCE2_log.ldf' , SIZE = 1024KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [MANAGER_FINANCE] SET COMPATIBILITY_LEVEL = 120
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [MANAGER_FINANCE].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [MANAGER_FINANCE] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [MANAGER_FINANCE] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [MANAGER_FINANCE] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [MANAGER_FINANCE] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [MANAGER_FINANCE] SET ARITHABORT OFF 
GO
ALTER DATABASE [MANAGER_FINANCE] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [MANAGER_FINANCE] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [MANAGER_FINANCE] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [MANAGER_FINANCE] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [MANAGER_FINANCE] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [MANAGER_FINANCE] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [MANAGER_FINANCE] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [MANAGER_FINANCE] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [MANAGER_FINANCE] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [MANAGER_FINANCE] SET  DISABLE_BROKER 
GO
ALTER DATABASE [MANAGER_FINANCE] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [MANAGER_FINANCE] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [MANAGER_FINANCE] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [MANAGER_FINANCE] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [MANAGER_FINANCE] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [MANAGER_FINANCE] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [MANAGER_FINANCE] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [MANAGER_FINANCE] SET RECOVERY FULL 
GO
ALTER DATABASE [MANAGER_FINANCE] SET  MULTI_USER 
GO
ALTER DATABASE [MANAGER_FINANCE] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [MANAGER_FINANCE] SET DB_CHAINING OFF 
GO
ALTER DATABASE [MANAGER_FINANCE] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [MANAGER_FINANCE] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
ALTER DATABASE [MANAGER_FINANCE] SET DELAYED_DURABILITY = DISABLED 
GO
EXEC sys.sp_db_vardecimal_storage_format N'MANAGER_FINANCE', N'ON'
GO
USE [MANAGER_FINANCE]
GO
/****** Object:  Table [dbo].[account]    Script Date: 8/14/2023 11:56:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[account](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[role_id] [int] NOT NULL,
	[email] [nvarchar](45) NOT NULL,
	[username] [nvarchar](45) NOT NULL,
	[password] [nvarchar](255) NOT NULL,
	[is_activity] [bit] NOT NULL,
	[verify_code] [nvarchar](50) NULL,
 CONSTRAINT [PK_account] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[auth_token]    Script Date: 8/14/2023 11:56:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[auth_token](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[user_id] [int] NOT NULL,
	[token_id] [nvarchar](255) NOT NULL,
	[expired_time] [bigint] NOT NULL,
 CONSTRAINT [PK_auth_token] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[budget]    Script Date: 8/14/2023 11:56:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[budget](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[user_category_id] [int] NOT NULL,
	[amount] [decimal](15, 3) NOT NULL,
	[fromdate] [date] NOT NULL,
	[todate] [date] NOT NULL,
	[description] [nvarchar](255) NULL,
 CONSTRAINT [PK_budget_1] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[category_type]    Script Date: 8/14/2023 11:56:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[category_type](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](100) NOT NULL,
 CONSTRAINT [PK_category] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[goal]    Script Date: 8/14/2023 11:56:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[goal](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[user_id] [int] NOT NULL,
	[name] [nvarchar](100) NOT NULL,
	[amount] [decimal](15, 2) NOT NULL,
	[deadline] [date] NOT NULL,
	[status] [nvarchar](20) NOT NULL,
 CONSTRAINT [PK_goal] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[goal_progress]    Script Date: 8/14/2023 11:56:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[goal_progress](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[goal_id] [int] NOT NULL,
	[deposit] [decimal](15, 2) NOT NULL,
	[date_add_deposit] [date] NOT NULL,
 CONSTRAINT [PK_goal_detail] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[notification]    Script Date: 8/14/2023 11:56:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[notification](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[user_id] [int] NOT NULL,
	[name] [nvarchar](50) NOT NULL,
	[detail] [nvarchar](100) NOT NULL,
	[date] [date] NOT NULL,
	[status] [nvarchar](20) NOT NULL,
 CONSTRAINT [PK_notification] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[roles_auth]    Script Date: 8/14/2023 11:56:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[roles_auth](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](20) NOT NULL,
 CONSTRAINT [PK_role_auth] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[transaction_type]    Script Date: 8/14/2023 11:56:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[transaction_type](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](100) NOT NULL,
 CONSTRAINT [PK_Table_1] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[transactions]    Script Date: 8/14/2023 11:56:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[transactions](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[user_category_id] [int] NOT NULL,
	[transaction_type_id] [int] NOT NULL,
	[name] [nvarchar](100) NOT NULL,
	[amount] [decimal](15, 3) NOT NULL,
	[location] [nvarchar](255) NOT NULL,
	[date] [datetime] NOT NULL,
	[description] [nvarchar](255) NULL,
 CONSTRAINT [PK_transaction_1] PRIMARY KEY CLUSTERED 
(
	[id] ASC,
	[user_category_id] ASC,
	[transaction_type_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[user_category]    Script Date: 8/14/2023 11:56:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[user_category](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[user_id] [int] NOT NULL,
	[category_type_id] [int] NOT NULL,
	[name] [nvarchar](100) NOT NULL,
	[color] [nvarchar](50) NOT NULL,
	[description] [nvarchar](255) NULL,
 CONSTRAINT [PK_user_category] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[user_information]    Script Date: 8/14/2023 11:56:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[user_information](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[date_created] [date] NOT NULL,
	[account_id] [int] NOT NULL,
	[firstname] [nvarchar](50) NOT NULL,
	[lastname] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_user] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET IDENTITY_INSERT [dbo].[account] ON 

INSERT [dbo].[account] ([id], [role_id], [email], [username], [password], [is_activity], [verify_code]) VALUES (7, 1, N'tranhuutruong290401@gmail.com', N'tranhuutruong', N'$2a$10$e8sur6a12qWN4RlUSD/X3.032qaA6TWlsYWUJKniaD7l.IZofaBwu', 1, N'')
INSERT [dbo].[account] ([id], [role_id], [email], [username], [password], [is_activity], [verify_code]) VALUES (8, 2, N'tranhuuthanh290603@gmail.com', N'tranhuuthanh', N'$2a$10$cjFl5/T9iO05ICk9yMHpHO2FGDM75WlfgMtvrlHMDgGVWHzhU4hD2', 1, N'7cd3c43e-c')
INSERT [dbo].[account] ([id], [role_id], [email], [username], [password], [is_activity], [verify_code]) VALUES (9, 2, N'lequocthien@gmail.com', N'lequocthien', N'$2a$10$pkvL153UZzrgpmnO7iGPTugwCEr3E.8FHKiB/NK3NxdNKBGP4soRu', 1, N'')
INSERT [dbo].[account] ([id], [role_id], [email], [username], [password], [is_activity], [verify_code]) VALUES (10, 2, N'nguyenthanhtrung@gmail.com', N'nguyenthanhtrung', N'$2a$10$w20q50TByDujSmfv6u1cHOxRjXDHSCqmmrm2JpatKWK/REI/j0zDq', 1, N'')
SET IDENTITY_INSERT [dbo].[account] OFF
SET IDENTITY_INSERT [dbo].[auth_token] ON 

INSERT [dbo].[auth_token] ([id], [user_id], [token_id], [expired_time]) VALUES (1, 3, N'889714ad-a88a-41f3-ad23-00f2f4caafd8', 1691331894663)
INSERT [dbo].[auth_token] ([id], [user_id], [token_id], [expired_time]) VALUES (2, 3, N'5c2e841a-f5d3-40de-b971-13e94daecafb', 1691336293201)
INSERT [dbo].[auth_token] ([id], [user_id], [token_id], [expired_time]) VALUES (1002, 3, N'616fcd3b-6943-410f-8d32-0c6a535cfdac', 1691420758326)
INSERT [dbo].[auth_token] ([id], [user_id], [token_id], [expired_time]) VALUES (2002, 2, N'c650ca98-abaf-4926-924e-8e6a144b50a9', 1691437396650)
INSERT [dbo].[auth_token] ([id], [user_id], [token_id], [expired_time]) VALUES (2003, 3, N'd47f0ba9-6939-4575-9a55-6064408df76d', 1691439704372)
INSERT [dbo].[auth_token] ([id], [user_id], [token_id], [expired_time]) VALUES (2004, 3, N'b84dcbbe-45e3-4889-8b7c-d2177e1249e3', 1691443457169)
INSERT [dbo].[auth_token] ([id], [user_id], [token_id], [expired_time]) VALUES (2005, 3, N'04669e68-3f34-4799-b689-0f978613edaf', 1691445432693)
INSERT [dbo].[auth_token] ([id], [user_id], [token_id], [expired_time]) VALUES (2006, 3, N'0aceba7a-b9a7-48f1-8913-a764392a3f1c', 1691445782863)
INSERT [dbo].[auth_token] ([id], [user_id], [token_id], [expired_time]) VALUES (2007, 3, N'b85dff92-31bc-4074-8eec-b269ce0f7313', 1691446067126)
INSERT [dbo].[auth_token] ([id], [user_id], [token_id], [expired_time]) VALUES (2008, 2, N'dea5c7f1-12e0-43bf-8cf3-48be2079f55d', 1691446500980)
INSERT [dbo].[auth_token] ([id], [user_id], [token_id], [expired_time]) VALUES (2009, 3, N'00acf2e1-7ed4-4733-be8b-dac689d971a6', 1691446520738)
INSERT [dbo].[auth_token] ([id], [user_id], [token_id], [expired_time]) VALUES (2010, 3, N'9c16e472-c9dd-4e78-8901-552235f4dab4', 1691448590507)
INSERT [dbo].[auth_token] ([id], [user_id], [token_id], [expired_time]) VALUES (2011, 3, N'a02b4e57-7bb8-4fad-8f9e-565413174e6a', 1691760717597)
INSERT [dbo].[auth_token] ([id], [user_id], [token_id], [expired_time]) VALUES (2012, 3, N'f3ba301d-e0a9-4801-9ee0-90a899a2a70e', 1691764431656)
INSERT [dbo].[auth_token] ([id], [user_id], [token_id], [expired_time]) VALUES (2013, 3, N'478698c1-1e31-4445-9b1c-9dbc146d082b', 1692027761694)
INSERT [dbo].[auth_token] ([id], [user_id], [token_id], [expired_time]) VALUES (2014, 3, N'bee86e88-2a40-4fe5-b186-870b1e49d754', 1692027826477)
INSERT [dbo].[auth_token] ([id], [user_id], [token_id], [expired_time]) VALUES (2015, 3, N'c0d087c0-fbdb-4d57-90fb-b928eb21bb01', 1692027980452)
INSERT [dbo].[auth_token] ([id], [user_id], [token_id], [expired_time]) VALUES (2016, 3, N'7585de5e-8256-4a94-a0da-7b2c650058b3', 1692059706411)
INSERT [dbo].[auth_token] ([id], [user_id], [token_id], [expired_time]) VALUES (2017, 3, N'42f88382-0171-4019-9d8e-8bc9e4f04278', 1692061367208)
INSERT [dbo].[auth_token] ([id], [user_id], [token_id], [expired_time]) VALUES (2018, 3, N'9c0660e6-cf22-457a-93a8-25679319b884', 1692062120690)
INSERT [dbo].[auth_token] ([id], [user_id], [token_id], [expired_time]) VALUES (2019, 2, N'dcddfab6-4226-464a-a50f-e861a73ed790', 1692062180259)
INSERT [dbo].[auth_token] ([id], [user_id], [token_id], [expired_time]) VALUES (2020, 3, N'bd80a224-d11a-40dd-9aa0-8b1079ae6050', 1692062300512)
INSERT [dbo].[auth_token] ([id], [user_id], [token_id], [expired_time]) VALUES (2021, 3, N'8577d873-ff3e-4e93-b949-2d76204cbad0', 1692066457413)
INSERT [dbo].[auth_token] ([id], [user_id], [token_id], [expired_time]) VALUES (2022, 3, N'd4353624-8aa3-48dd-9fde-7cf58b2c888d', 1692066961065)
INSERT [dbo].[auth_token] ([id], [user_id], [token_id], [expired_time]) VALUES (2023, 3, N'039b470c-627c-4ec6-8cf7-2b6b58fd2859', 1692067014756)
INSERT [dbo].[auth_token] ([id], [user_id], [token_id], [expired_time]) VALUES (2024, 3, N'392eea95-d3cd-46bd-9f5e-2c813bf6a50d', 1692067070373)
SET IDENTITY_INSERT [dbo].[auth_token] OFF
SET IDENTITY_INSERT [dbo].[budget] ON 

INSERT [dbo].[budget] ([id], [user_category_id], [amount], [fromdate], [todate], [description]) VALUES (1, 2, CAST(1000000.000 AS Decimal(15, 3)), CAST(N'2023-07-01' AS Date), CAST(N'2023-07-31' AS Date), N'abc')
INSERT [dbo].[budget] ([id], [user_category_id], [amount], [fromdate], [todate], [description]) VALUES (7, 8, CAST(2000000.000 AS Decimal(15, 3)), CAST(N'2023-07-01' AS Date), CAST(N'2023-07-31' AS Date), N'abc')
SET IDENTITY_INSERT [dbo].[budget] OFF
SET IDENTITY_INSERT [dbo].[category_type] ON 

INSERT [dbo].[category_type] ([id], [name]) VALUES (1, N'Thu nhập')
INSERT [dbo].[category_type] ([id], [name]) VALUES (2, N'Chi tiêu')
SET IDENTITY_INSERT [dbo].[category_type] OFF
SET IDENTITY_INSERT [dbo].[goal] ON 

INSERT [dbo].[goal] ([id], [user_id], [name], [amount], [deadline], [status]) VALUES (2, 3, N'Xây nhà cho bố', CAST(500000000.00 AS Decimal(15, 2)), CAST(N'2025-08-29' AS Date), N'Chưa hoàn thành')
INSERT [dbo].[goal] ([id], [user_id], [name], [amount], [deadline], [status]) VALUES (7, 3, N'Mua laptop mới toanh', CAST(1500000.00 AS Decimal(15, 2)), CAST(N'2023-09-22' AS Date), N'Chưa hoàn thành')
INSERT [dbo].[goal] ([id], [user_id], [name], [amount], [deadline], [status]) VALUES (10, 3, N'Mua abc', CAST(500000.00 AS Decimal(15, 2)), CAST(N'2023-08-30' AS Date), N'Chưa hoàn thành')
SET IDENTITY_INSERT [dbo].[goal] OFF
SET IDENTITY_INSERT [dbo].[goal_progress] ON 

INSERT [dbo].[goal_progress] ([id], [goal_id], [deposit], [date_add_deposit]) VALUES (1, 2, CAST(5000000.00 AS Decimal(15, 2)), CAST(N'2023-07-16' AS Date))
INSERT [dbo].[goal_progress] ([id], [goal_id], [deposit], [date_add_deposit]) VALUES (2, 2, CAST(5000000.00 AS Decimal(15, 2)), CAST(N'2023-07-16' AS Date))
INSERT [dbo].[goal_progress] ([id], [goal_id], [deposit], [date_add_deposit]) VALUES (3, 2, CAST(500000.00 AS Decimal(15, 2)), CAST(N'2023-07-17' AS Date))
INSERT [dbo].[goal_progress] ([id], [goal_id], [deposit], [date_add_deposit]) VALUES (16, 2, CAST(500000.00 AS Decimal(15, 2)), CAST(N'2023-07-17' AS Date))
INSERT [dbo].[goal_progress] ([id], [goal_id], [deposit], [date_add_deposit]) VALUES (22, 2, CAST(100000.00 AS Decimal(15, 2)), CAST(N'2023-07-17' AS Date))
INSERT [dbo].[goal_progress] ([id], [goal_id], [deposit], [date_add_deposit]) VALUES (23, 2, CAST(100000.00 AS Decimal(15, 2)), CAST(N'2023-07-17' AS Date))
INSERT [dbo].[goal_progress] ([id], [goal_id], [deposit], [date_add_deposit]) VALUES (24, 2, CAST(100000.00 AS Decimal(15, 2)), CAST(N'2023-07-17' AS Date))
INSERT [dbo].[goal_progress] ([id], [goal_id], [deposit], [date_add_deposit]) VALUES (27, 7, CAST(100000.00 AS Decimal(15, 2)), CAST(N'2023-07-18' AS Date))
INSERT [dbo].[goal_progress] ([id], [goal_id], [deposit], [date_add_deposit]) VALUES (30, 2, CAST(200000.00 AS Decimal(15, 2)), CAST(N'2023-07-22' AS Date))
INSERT [dbo].[goal_progress] ([id], [goal_id], [deposit], [date_add_deposit]) VALUES (31, 10, CAST(100000.00 AS Decimal(15, 2)), CAST(N'2023-08-04' AS Date))
SET IDENTITY_INSERT [dbo].[goal_progress] OFF
SET IDENTITY_INSERT [dbo].[notification] ON 

INSERT [dbo].[notification] ([id], [user_id], [name], [detail], [date], [status]) VALUES (15, 3, N'Ví dụ thôi nha các bạn', N'acv', CAST(N'2023-07-30' AS Date), N'Chưa đọc')
SET IDENTITY_INSERT [dbo].[notification] OFF
SET IDENTITY_INSERT [dbo].[roles_auth] ON 

INSERT [dbo].[roles_auth] ([id], [name]) VALUES (1, N'ADMIN')
INSERT [dbo].[roles_auth] ([id], [name]) VALUES (2, N'USER')
SET IDENTITY_INSERT [dbo].[roles_auth] OFF
SET IDENTITY_INSERT [dbo].[transaction_type] ON 

INSERT [dbo].[transaction_type] ([id], [name]) VALUES (1, N'Gia đình')
INSERT [dbo].[transaction_type] ([id], [name]) VALUES (2, N'Cá nhân')
SET IDENTITY_INSERT [dbo].[transaction_type] OFF
SET IDENTITY_INSERT [dbo].[transactions] ON 

INSERT [dbo].[transactions] ([id], [user_category_id], [transaction_type_id], [name], [amount], [location], [date], [description]) VALUES (1, 2, 1, N'Mua blabla', CAST(100000.000 AS Decimal(15, 3)), N'abc', CAST(N'2023-07-18 00:00:00.000' AS DateTime), N'abc')
INSERT [dbo].[transactions] ([id], [user_category_id], [transaction_type_id], [name], [amount], [location], [date], [description]) VALUES (4, 2, 2, N'Bla', CAST(200000.000 AS Decimal(15, 3)), N'acv', CAST(N'2023-07-12 00:00:00.000' AS DateTime), N'aaa')
INSERT [dbo].[transactions] ([id], [user_category_id], [transaction_type_id], [name], [amount], [location], [date], [description]) VALUES (6, 8, 2, N'Mua quần jean', CAST(200000.000 AS Decimal(15, 3)), N'Quận 9', CAST(N'2023-07-22 00:00:00.000' AS DateTime), N'Mua quần')
INSERT [dbo].[transactions] ([id], [user_category_id], [transaction_type_id], [name], [amount], [location], [date], [description]) VALUES (7, 2, 2, N'Mua sách', CAST(50000.000 AS Decimal(15, 3)), N'Nhà sách Phương Nam Quận 9', CAST(N'2023-06-30 00:00:00.000' AS DateTime), N'Mua sách IT')
INSERT [dbo].[transactions] ([id], [user_category_id], [transaction_type_id], [name], [amount], [location], [date], [description]) VALUES (8, 11, 1, N'Tiền điện tháng 7', CAST(500000.000 AS Decimal(15, 3)), N'Điện lực quận 9', CAST(N'2023-07-30 00:00:00.000' AS DateTime), N'Tiền điện hàng tháng')
INSERT [dbo].[transactions] ([id], [user_category_id], [transaction_type_id], [name], [amount], [location], [date], [description]) VALUES (9, 10, 2, N'Lương tháng 7', CAST(2000000.000 AS Decimal(15, 3)), N'VNPT', CAST(N'2023-07-30 00:00:00.000' AS DateTime), N'Lương')
INSERT [dbo].[transactions] ([id], [user_category_id], [transaction_type_id], [name], [amount], [location], [date], [description]) VALUES (10, 8, 2, N'Mua áo thun', CAST(50000.000 AS Decimal(15, 3)), N'Shopppe', CAST(N'2023-07-26 00:00:00.000' AS DateTime), N'Mua sắm vừa phải')
INSERT [dbo].[transactions] ([id], [user_category_id], [transaction_type_id], [name], [amount], [location], [date], [description]) VALUES (12, 11, 1, N'Tiền điện tháng 8', CAST(300000.000 AS Decimal(15, 3)), N'Điện lực quận 9', CAST(N'2023-08-30 00:00:00.000' AS DateTime), N'Tiền điện tháng 8')
INSERT [dbo].[transactions] ([id], [user_category_id], [transaction_type_id], [name], [amount], [location], [date], [description]) VALUES (13, 14, 2, N'Tiền trọ tháng 8', CAST(750000.000 AS Decimal(15, 3)), N'Khu Cảnh vệ', CAST(N'2023-08-05 00:00:00.000' AS DateTime), N'Tiền trọ tháng 8, đóng cho Khánh')
SET IDENTITY_INSERT [dbo].[transactions] OFF
SET IDENTITY_INSERT [dbo].[user_category] ON 

INSERT [dbo].[user_category] ([id], [user_id], [category_type_id], [name], [color], [description]) VALUES (2, 3, 2, N'Việc học tập cá nhân', N'#ec3535', N'abc')
INSERT [dbo].[user_category] ([id], [user_id], [category_type_id], [name], [color], [description]) VALUES (7, 3, 1, N'Tiết kiệm', N'#26e518', N'Tiết kiệm')
INSERT [dbo].[user_category] ([id], [user_id], [category_type_id], [name], [color], [description]) VALUES (8, 3, 2, N'Mua sắm', N'#97bc94', N'Mua sắm linh tinh')
INSERT [dbo].[user_category] ([id], [user_id], [category_type_id], [name], [color], [description]) VALUES (9, 4, 2, N'Mua sắm', N'#1677FF', N'Mua sắm linh tinh')
INSERT [dbo].[user_category] ([id], [user_id], [category_type_id], [name], [color], [description]) VALUES (10, 3, 1, N'Lương', N'#2d7be8', N'Lương hàng tháng')
INSERT [dbo].[user_category] ([id], [user_id], [category_type_id], [name], [color], [description]) VALUES (11, 3, 2, N'Tiền điện', N'#1677FF', N'Tiền điện hàng tháng')
INSERT [dbo].[user_category] ([id], [user_id], [category_type_id], [name], [color], [description]) VALUES (12, 3, 2, N'Tiền nước', N'#3fefef', N'Tiền nước hàng tháng')
INSERT [dbo].[user_category] ([id], [user_id], [category_type_id], [name], [color], [description]) VALUES (14, 3, 2, N'Tiền trọ', N'#ff2bec', N'Tiền trọ hàng tháng')
SET IDENTITY_INSERT [dbo].[user_category] OFF
SET IDENTITY_INSERT [dbo].[user_information] ON 

INSERT [dbo].[user_information] ([id], [date_created], [account_id], [firstname], [lastname]) VALUES (2, CAST(N'2023-07-14' AS Date), 7, N'Trần Hữu', N'Trưởng')
INSERT [dbo].[user_information] ([id], [date_created], [account_id], [firstname], [lastname]) VALUES (3, CAST(N'2023-07-14' AS Date), 8, N'Trần Hữu', N'Thành')
INSERT [dbo].[user_information] ([id], [date_created], [account_id], [firstname], [lastname]) VALUES (4, CAST(N'2023-07-16' AS Date), 9, N'Lê Quốc', N'Thiên')
INSERT [dbo].[user_information] ([id], [date_created], [account_id], [firstname], [lastname]) VALUES (5, CAST(N'2023-08-11' AS Date), 10, N'Nguyễn Thành', N'Trung')
SET IDENTITY_INSERT [dbo].[user_information] OFF
/****** Object:  Index [UQ_id_account]    Script Date: 8/14/2023 11:56:55 PM ******/
ALTER TABLE [dbo].[user_information] ADD  CONSTRAINT [UQ_id_account] UNIQUE NONCLUSTERED 
(
	[account_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
ALTER TABLE [dbo].[account]  WITH CHECK ADD  CONSTRAINT [FK_account_role] FOREIGN KEY([role_id])
REFERENCES [dbo].[roles_auth] ([id])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[account] CHECK CONSTRAINT [FK_account_role]
GO
ALTER TABLE [dbo].[auth_token]  WITH CHECK ADD  CONSTRAINT [FK_auth_token_users_information] FOREIGN KEY([user_id])
REFERENCES [dbo].[user_information] ([id])
GO
ALTER TABLE [dbo].[auth_token] CHECK CONSTRAINT [FK_auth_token_users_information]
GO
ALTER TABLE [dbo].[budget]  WITH CHECK ADD  CONSTRAINT [FK_budget_user_category] FOREIGN KEY([user_category_id])
REFERENCES [dbo].[user_category] ([id])
GO
ALTER TABLE [dbo].[budget] CHECK CONSTRAINT [FK_budget_user_category]
GO
ALTER TABLE [dbo].[goal]  WITH CHECK ADD  CONSTRAINT [FK_goal_users_information] FOREIGN KEY([user_id])
REFERENCES [dbo].[user_information] ([id])
GO
ALTER TABLE [dbo].[goal] CHECK CONSTRAINT [FK_goal_users_information]
GO
ALTER TABLE [dbo].[goal_progress]  WITH CHECK ADD  CONSTRAINT [FK_goal_detail_goal1] FOREIGN KEY([goal_id])
REFERENCES [dbo].[goal] ([id])
GO
ALTER TABLE [dbo].[goal_progress] CHECK CONSTRAINT [FK_goal_detail_goal1]
GO
ALTER TABLE [dbo].[notification]  WITH CHECK ADD  CONSTRAINT [FK_notifications_users_information] FOREIGN KEY([user_id])
REFERENCES [dbo].[user_information] ([id])
GO
ALTER TABLE [dbo].[notification] CHECK CONSTRAINT [FK_notifications_users_information]
GO
ALTER TABLE [dbo].[transactions]  WITH CHECK ADD  CONSTRAINT [FK_transaction_transaction_type] FOREIGN KEY([transaction_type_id])
REFERENCES [dbo].[transaction_type] ([id])
GO
ALTER TABLE [dbo].[transactions] CHECK CONSTRAINT [FK_transaction_transaction_type]
GO
ALTER TABLE [dbo].[transactions]  WITH CHECK ADD  CONSTRAINT [FK_transaction_user_category] FOREIGN KEY([user_category_id])
REFERENCES [dbo].[user_category] ([id])
GO
ALTER TABLE [dbo].[transactions] CHECK CONSTRAINT [FK_transaction_user_category]
GO
ALTER TABLE [dbo].[user_category]  WITH CHECK ADD  CONSTRAINT [FK_user_category_category] FOREIGN KEY([category_type_id])
REFERENCES [dbo].[category_type] ([id])
GO
ALTER TABLE [dbo].[user_category] CHECK CONSTRAINT [FK_user_category_category]
GO
ALTER TABLE [dbo].[user_category]  WITH CHECK ADD  CONSTRAINT [FK_user_category_users_information] FOREIGN KEY([user_id])
REFERENCES [dbo].[user_information] ([id])
GO
ALTER TABLE [dbo].[user_category] CHECK CONSTRAINT [FK_user_category_users_information]
GO
ALTER TABLE [dbo].[user_information]  WITH CHECK ADD  CONSTRAINT [FK_user_account] FOREIGN KEY([account_id])
REFERENCES [dbo].[account] ([id])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[user_information] CHECK CONSTRAINT [FK_user_account]
GO
ALTER TABLE [dbo].[budget]  WITH CHECK ADD  CONSTRAINT [CK_budget] CHECK  (([amount]>(0)))
GO
ALTER TABLE [dbo].[budget] CHECK CONSTRAINT [CK_budget]
GO
ALTER TABLE [dbo].[goal]  WITH CHECK ADD  CONSTRAINT [CK_amount] CHECK  (([amount]>(0)))
GO
ALTER TABLE [dbo].[goal] CHECK CONSTRAINT [CK_amount]
GO
ALTER TABLE [dbo].[goal_progress]  WITH CHECK ADD  CONSTRAINT [CK_deposit] CHECK  (([deposit]>(0)))
GO
ALTER TABLE [dbo].[goal_progress] CHECK CONSTRAINT [CK_deposit]
GO
ALTER TABLE [dbo].[transactions]  WITH CHECK ADD  CONSTRAINT [CK_amount_transaction] CHECK  (([amount]>(0)))
GO
ALTER TABLE [dbo].[transactions] CHECK CONSTRAINT [CK_amount_transaction]
GO
/****** Object:  StoredProcedure [dbo].[GetTransactionByMonthAndFamily]    Script Date: 8/14/2023 11:56:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[GetTransactionByMonthAndFamily]
	@MONTH INT,
	@USER_ID INT
AS
BEGIN
	SELECT t.id as id, t.name as name, t.amount as amount, t.location as location, t.date as date, t.description as description, tt.name as transaction_type_name, uc.name as name
	FROM transactions t
	JOIN transaction_type tt ON t.transaction_type_id = tt.id
	JOIN user_category uc ON t.user_category_id = uc.id
	WHERE MONTH(t.date) = @MONTH AND tt.name = N'Family' AND uc.user_id = @USER_ID
	ORDER BY uc.name ASC
END
GO
/****** Object:  StoredProcedure [dbo].[GetTransactionByMonthAndPersonal]    Script Date: 8/14/2023 11:56:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[GetTransactionByMonthAndPersonal]
	@MONTH INT,
	@USER_ID INT
AS
BEGIN
	SELECT t.id as id, t.name as name, t.amount as amount, t.location as location, t.date as date, t.description as description, tt.name as transaction_type_name, uc.name as name_category 
	FROM transactions t
	JOIN transaction_type tt ON t.transaction_type_id = tt.id
	JOIN user_category uc ON t.user_category_id = uc.id
	WHERE MONTH(t.date) = @MONTH AND tt.name = 'Personal' AND uc.user_id = @USER_ID
	ORDER BY uc.name ASC
END
GO
USE [master]
GO
ALTER DATABASE [MANAGER_FINANCE] SET  READ_WRITE 
GO
