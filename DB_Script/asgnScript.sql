USE [master]
GO
/****** Object:  Database [ISC301_Assgn]    Script Date: 09-Jul-21 10:10:35 PM ******/
CREATE DATABASE [ISC301_Assgn]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'ISC301_Assgn', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL14.MSSQLSERVER\MSSQL\DATA\ISC301_Assgn.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'ISC301_Assgn_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL14.MSSQLSERVER\MSSQL\DATA\ISC301_Assgn_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
GO
ALTER DATABASE [ISC301_Assgn] SET COMPATIBILITY_LEVEL = 140
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [ISC301_Assgn].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [ISC301_Assgn] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [ISC301_Assgn] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [ISC301_Assgn] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [ISC301_Assgn] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [ISC301_Assgn] SET ARITHABORT OFF 
GO
ALTER DATABASE [ISC301_Assgn] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [ISC301_Assgn] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [ISC301_Assgn] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [ISC301_Assgn] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [ISC301_Assgn] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [ISC301_Assgn] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [ISC301_Assgn] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [ISC301_Assgn] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [ISC301_Assgn] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [ISC301_Assgn] SET  DISABLE_BROKER 
GO
ALTER DATABASE [ISC301_Assgn] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [ISC301_Assgn] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [ISC301_Assgn] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [ISC301_Assgn] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [ISC301_Assgn] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [ISC301_Assgn] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [ISC301_Assgn] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [ISC301_Assgn] SET RECOVERY FULL 
GO
ALTER DATABASE [ISC301_Assgn] SET  MULTI_USER 
GO
ALTER DATABASE [ISC301_Assgn] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [ISC301_Assgn] SET DB_CHAINING OFF 
GO
ALTER DATABASE [ISC301_Assgn] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [ISC301_Assgn] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [ISC301_Assgn] SET DELAYED_DURABILITY = DISABLED 
GO
EXEC sys.sp_db_vardecimal_storage_format N'ISC301_Assgn', N'ON'
GO
ALTER DATABASE [ISC301_Assgn] SET QUERY_STORE = OFF
GO
USE [ISC301_Assgn]
GO
/****** Object:  Table [dbo].[addressTbl]    Script Date: 09-Jul-21 10:10:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[addressTbl](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[username] [nvarchar](32) NULL,
	[phone] [nvarchar](16) NULL,
	[name] [nvarchar](64) NULL,
	[addressLine] [nvarchar](128) NULL,
	[addressWard] [nvarchar](64) NULL,
	[addressDistrict] [nvarchar](64) NULL,
	[addressCity] [nvarchar](64) NULL,
	[status] [int] NULL,
 CONSTRAINT [PK_addressTbl] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[cakeTbl]    Script Date: 09-Jul-21 10:10:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[cakeTbl](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](64) NOT NULL,
	[description] [nvarchar](max) NULL,
	[price] [float] NOT NULL,
	[createDate] [date] NOT NULL,
	[expireDate] [date] NOT NULL,
	[quantity] [int] NOT NULL,
	[status] [int] NOT NULL,
	[categoryID] [int] NOT NULL,
	[image] [bit] NULL,
 CONSTRAINT [PK_cakeTbl] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[categoryTbl]    Script Date: 09-Jul-21 10:10:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[categoryTbl](
	[id] [int] NOT NULL,
	[name] [nvarchar](32) NULL,
 CONSTRAINT [PK_categoryTbl] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[orderItemTbl]    Script Date: 09-Jul-21 10:10:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[orderItemTbl](
	[orderID] [int] NOT NULL,
	[itemID] [int] NOT NULL,
	[itemName] [nvarchar](64) NULL,
	[itemQuantity] [int] NULL,
	[itemTotal] [float] NULL,
 CONSTRAINT [PK_orderItemTbl] PRIMARY KEY CLUSTERED 
(
	[orderID] ASC,
	[itemID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[orderTbl]    Script Date: 09-Jul-21 10:10:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[orderTbl](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[userID] [nvarchar](32) NULL,
	[orderDate] [datetime] NULL,
	[paymentMethod] [int] NULL,
	[paymentStatus] [int] NULL,
	[address] [int] NULL,
	[paymentDate] [datetime] NULL,
 CONSTRAINT [PK_orderTbl] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[paymentMethodTbl]    Script Date: 09-Jul-21 10:10:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[paymentMethodTbl](
	[id] [int] NOT NULL,
	[methodName] [nvarchar](32) NULL,
 CONSTRAINT [PK_paymentMethodTbl] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[paymentStatusTbl]    Script Date: 09-Jul-21 10:10:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[paymentStatusTbl](
	[id] [int] NOT NULL,
	[statusName] [nvarchar](16) NULL,
 CONSTRAINT [PK_paymentStatusTbl] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[roleTbl]    Script Date: 09-Jul-21 10:10:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[roleTbl](
	[roleID] [int] NOT NULL,
	[roleName] [nvarchar](16) NULL,
 CONSTRAINT [PK_roleTbl] PRIMARY KEY CLUSTERED 
(
	[roleID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[statusTbl]    Script Date: 09-Jul-21 10:10:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[statusTbl](
	[id] [int] NOT NULL,
	[name] [nvarchar](16) NULL,
 CONSTRAINT [PK_statusTbl] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[userTbl]    Script Date: 09-Jul-21 10:10:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[userTbl](
	[username] [nvarchar](32) NOT NULL,
	[password] [nvarchar](64) NULL,
	[nameFirst] [nvarchar](64) NULL,
	[nameLast] [nvarchar](64) NULL,
	[role] [int] NULL,
	[status] [int] NULL,
 CONSTRAINT [PK_userTbl] PRIMARY KEY CLUSTERED 
(
	[username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[addressTbl] ON 

INSERT [dbo].[addressTbl] ([id], [username], [phone], [name], [addressLine], [addressWard], [addressDistrict], [addressCity], [status]) VALUES (1, N'abc@gmail.com', N'1412512519', N'abc cde', N'adsfghj', N'234567ui', N'dfsghjk', N'fdxghj', 1)
INSERT [dbo].[addressTbl] ([id], [username], [phone], [name], [addressLine], [addressWard], [addressDistrict], [addressCity], [status]) VALUES (2, N'abc@gmail.com', N'14125125191', N'abc cde', N'adsfghj', N'234567ui', N'dfsghjk', N'fdxghj', 1)
SET IDENTITY_INSERT [dbo].[addressTbl] OFF
SET IDENTITY_INSERT [dbo].[cakeTbl] ON 

INSERT [dbo].[cakeTbl] ([id], [name], [description], [price], [createDate], [expireDate], [quantity], [status], [categoryID], [image]) VALUES (1, N'BLT', N'The good ol'' Bacon, Lettuce and Tomato sandwich that everybody loves', 16.950000762939453, CAST(N'2021-07-08' AS Date), CAST(N'2021-07-16' AS Date), 21, 1, 0, 1)
INSERT [dbo].[cakeTbl] ([id], [name], [description], [price], [createDate], [expireDate], [quantity], [status], [categoryID], [image]) VALUES (2, N'Red Velvet Cake', N'Red velvet cake is a traditionally crimson-colored chocolate layer cake with cream cheese icing. It''s usually made with a combination of buttermilk, butter, cocoa, vinegar, and flour.', 129.99000549316406, CAST(N'2021-07-08' AS Date), CAST(N'2021-07-14' AS Date), 5, 1, 3, 1)
INSERT [dbo].[cakeTbl] ([id], [name], [description], [price], [createDate], [expireDate], [quantity], [status], [categoryID], [image]) VALUES (3, N'Chocolate Cake', N'Chocolate cake or chocolate gÃ¢teau is a cake flavored with melted chocolate, cocoa powder, or both.', 149.99000549316406, CAST(N'2021-07-08' AS Date), CAST(N'2021-07-14' AS Date), 1, 1, 3, 1)
SET IDENTITY_INSERT [dbo].[cakeTbl] OFF
INSERT [dbo].[categoryTbl] ([id], [name]) VALUES (0, N'Bread')
INSERT [dbo].[categoryTbl] ([id], [name]) VALUES (1, N'Sweet')
INSERT [dbo].[categoryTbl] ([id], [name]) VALUES (2, N'Candy')
INSERT [dbo].[categoryTbl] ([id], [name]) VALUES (3, N'Cake')
INSERT [dbo].[orderItemTbl] ([orderID], [itemID], [itemName], [itemQuantity], [itemTotal]) VALUES (4, 3, N'Chocolate Cake', 2, 299.98001098632812)
SET IDENTITY_INSERT [dbo].[orderTbl] ON 

INSERT [dbo].[orderTbl] ([id], [userID], [orderDate], [paymentMethod], [paymentStatus], [address], [paymentDate]) VALUES (4, N'abc@gmail.com', CAST(N'2021-07-09T21:56:04.613' AS DateTime), 0, 0, 1, NULL)
SET IDENTITY_INSERT [dbo].[orderTbl] OFF
INSERT [dbo].[paymentMethodTbl] ([id], [methodName]) VALUES (0, N'Cash On Delivery')
INSERT [dbo].[paymentStatusTbl] ([id], [statusName]) VALUES (0, N'Pending')
INSERT [dbo].[paymentStatusTbl] ([id], [statusName]) VALUES (1, N'Paid')
INSERT [dbo].[roleTbl] ([roleID], [roleName]) VALUES (0, N'Admin')
INSERT [dbo].[roleTbl] ([roleID], [roleName]) VALUES (1, N'Member')
INSERT [dbo].[roleTbl] ([roleID], [roleName]) VALUES (2, N'Guest')
INSERT [dbo].[statusTbl] ([id], [name]) VALUES (0, N'Inactive')
INSERT [dbo].[statusTbl] ([id], [name]) VALUES (1, N'Active')
INSERT [dbo].[userTbl] ([username], [password], [nameFirst], [nameLast], [role], [status]) VALUES (N'abc@gmail.com', N'34f2dc63b2c3a36f26103e912a3ddbbb34cfc95be8f7f03ea29339cccb2772e6', N'firstName', N'lastName', 1, 1)
INSERT [dbo].[userTbl] ([username], [password], [nameFirst], [nameLast], [role], [status]) VALUES (N'admin', N'34f2dc63b2c3a36f26103e912a3ddbbb34cfc95be8f7f03ea29339cccb2772e6', N'Admin', NULL, 0, 1)
ALTER TABLE [dbo].[addressTbl]  WITH CHECK ADD  CONSTRAINT [FK_addressTbl_statusTbl] FOREIGN KEY([status])
REFERENCES [dbo].[statusTbl] ([id])
GO
ALTER TABLE [dbo].[addressTbl] CHECK CONSTRAINT [FK_addressTbl_statusTbl]
GO
ALTER TABLE [dbo].[addressTbl]  WITH CHECK ADD  CONSTRAINT [FK_addressTbl_userTbl] FOREIGN KEY([username])
REFERENCES [dbo].[userTbl] ([username])
GO
ALTER TABLE [dbo].[addressTbl] CHECK CONSTRAINT [FK_addressTbl_userTbl]
GO
ALTER TABLE [dbo].[cakeTbl]  WITH CHECK ADD  CONSTRAINT [FK_cakeTbl_categoryTbl] FOREIGN KEY([categoryID])
REFERENCES [dbo].[categoryTbl] ([id])
GO
ALTER TABLE [dbo].[cakeTbl] CHECK CONSTRAINT [FK_cakeTbl_categoryTbl]
GO
ALTER TABLE [dbo].[cakeTbl]  WITH CHECK ADD  CONSTRAINT [FK_cakeTbl_statusTbl] FOREIGN KEY([status])
REFERENCES [dbo].[statusTbl] ([id])
GO
ALTER TABLE [dbo].[cakeTbl] CHECK CONSTRAINT [FK_cakeTbl_statusTbl]
GO
ALTER TABLE [dbo].[orderItemTbl]  WITH CHECK ADD  CONSTRAINT [FK_orderItemTbl_cakeTbl] FOREIGN KEY([itemID])
REFERENCES [dbo].[cakeTbl] ([id])
GO
ALTER TABLE [dbo].[orderItemTbl] CHECK CONSTRAINT [FK_orderItemTbl_cakeTbl]
GO
ALTER TABLE [dbo].[orderItemTbl]  WITH CHECK ADD  CONSTRAINT [FK_orderItemTbl_orderTbl] FOREIGN KEY([orderID])
REFERENCES [dbo].[orderTbl] ([id])
GO
ALTER TABLE [dbo].[orderItemTbl] CHECK CONSTRAINT [FK_orderItemTbl_orderTbl]
GO
ALTER TABLE [dbo].[orderTbl]  WITH CHECK ADD  CONSTRAINT [FK_orderTbl_addressTbl] FOREIGN KEY([address])
REFERENCES [dbo].[addressTbl] ([id])
GO
ALTER TABLE [dbo].[orderTbl] CHECK CONSTRAINT [FK_orderTbl_addressTbl]
GO
ALTER TABLE [dbo].[orderTbl]  WITH CHECK ADD  CONSTRAINT [FK_orderTbl_paymentMethodTbl] FOREIGN KEY([paymentMethod])
REFERENCES [dbo].[paymentMethodTbl] ([id])
GO
ALTER TABLE [dbo].[orderTbl] CHECK CONSTRAINT [FK_orderTbl_paymentMethodTbl]
GO
ALTER TABLE [dbo].[orderTbl]  WITH CHECK ADD  CONSTRAINT [FK_orderTbl_paymentStatusTbl] FOREIGN KEY([paymentStatus])
REFERENCES [dbo].[paymentStatusTbl] ([id])
GO
ALTER TABLE [dbo].[orderTbl] CHECK CONSTRAINT [FK_orderTbl_paymentStatusTbl]
GO
ALTER TABLE [dbo].[userTbl]  WITH CHECK ADD  CONSTRAINT [FK_userTbl_roleTbl1] FOREIGN KEY([role])
REFERENCES [dbo].[roleTbl] ([roleID])
GO
ALTER TABLE [dbo].[userTbl] CHECK CONSTRAINT [FK_userTbl_roleTbl1]
GO
ALTER TABLE [dbo].[userTbl]  WITH CHECK ADD  CONSTRAINT [FK_userTbl_statusTbl] FOREIGN KEY([status])
REFERENCES [dbo].[statusTbl] ([id])
GO
ALTER TABLE [dbo].[userTbl] CHECK CONSTRAINT [FK_userTbl_statusTbl]
GO
USE [master]
GO
ALTER DATABASE [ISC301_Assgn] SET  READ_WRITE 
GO
