/*
 Navicat Premium Data Transfer

 Source Server         : SQL Server
 Source Server Type    : SQL Server
 Source Server Version : 16001115 (16.00.1115)
 Source Host           : localhost:1433
 Source Catalog        : FinalWork
 Source Schema         : dbo

 Target Server Type    : SQL Server
 Target Server Version : 16001115 (16.00.1115)
 File Encoding         : 65001

 Date: 11/06/2024 14:02:24
*/


-- ----------------------------
-- Table structure for Company
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[Company]') AND type IN ('U'))
	DROP TABLE [dbo].[Company]
GO

CREATE TABLE [dbo].[Company] (
  [CompanyNumber] nchar(10) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [CompanyName] nchar(10) COLLATE Chinese_PRC_CI_AS  NULL,
  [CompanyAddress] nchar(50) COLLATE Chinese_PRC_CI_AS  NULL,
  [CompanyHotline] nchar(10) COLLATE Chinese_PRC_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[Company] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of Company
-- ----------------------------
INSERT INTO [dbo].[Company] ([CompanyNumber], [CompanyName], [CompanyAddress], [CompanyHotline]) VALUES (N'1         ', N'中国南方航空    ', N'广州市机场路278号95539                                   ', N'95539     ')
GO

INSERT INTO [dbo].[Company] ([CompanyNumber], [CompanyName], [CompanyAddress], [CompanyHotline]) VALUES (N'2         ', N'中国东方航空    ', N'上海市虹桥路2550号                                       ', N'95530     ')
GO

INSERT INTO [dbo].[Company] ([CompanyNumber], [CompanyName], [CompanyAddress], [CompanyHotline]) VALUES (N'3         ', N'中国国际航空    ', N'北京市顺义区天柱路30号                                      ', N'95583     ')
GO

INSERT INTO [dbo].[Company] ([CompanyNumber], [CompanyName], [CompanyAddress], [CompanyHotline]) VALUES (N'4         ', N'海南航空      ', N'海口市国兴大道7号                                         ', N'950718    ')
GO

INSERT INTO [dbo].[Company] ([CompanyNumber], [CompanyName], [CompanyAddress], [CompanyHotline]) VALUES (N'5         ', N'深圳航空      ', N'深圳宝安国际机场航站四路2033号                                 ', N'95080     ')
GO

INSERT INTO [dbo].[Company] ([CompanyNumber], [CompanyName], [CompanyAddress], [CompanyHotline]) VALUES (N'6         ', N'厦门航空      ', N'厦门市埭辽路22号                                         ', N'95557     ')
GO

INSERT INTO [dbo].[Company] ([CompanyNumber], [CompanyName], [CompanyAddress], [CompanyHotline]) VALUES (N'7         ', N'吉祥航空      ', N'上海市虹翔三路80号                                        ', N'95520     ')
GO


-- ----------------------------
-- Table structure for Flight
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[Flight]') AND type IN ('U'))
	DROP TABLE [dbo].[Flight]
GO

CREATE TABLE [dbo].[Flight] (
  [FlightNumber] varchar(10) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [companyID] nchar(10) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [FlightName] varchar(10) COLLATE Chinese_PRC_CI_AS  NULL,
  [fromCity] nchar(10) COLLATE Chinese_PRC_CI_AS  NULL,
  [toCity] nchar(10) COLLATE Chinese_PRC_CI_AS  NULL,
  [mileAge] int  NULL,
  [departureTime] time(7)  NULL
)
GO

ALTER TABLE [dbo].[Flight] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of Flight
-- ----------------------------
INSERT INTO [dbo].[Flight] ([FlightNumber], [companyID], [FlightName], [fromCity], [toCity], [mileAge], [departureTime]) VALUES (N'3U8948', N'8         ', N'空客320', N'济南        ', N'昆明        ', N'2080', N'21:30:00.0000000')
GO

INSERT INTO [dbo].[Flight] ([FlightNumber], [companyID], [FlightName], [fromCity], [toCity], [mileAge], [departureTime]) VALUES (N'3U8962', N'8         ', N'空客320', N'上海        ', N'成都        ', N'2800', N'11:35:00.0000000')
GO

INSERT INTO [dbo].[Flight] ([FlightNumber], [companyID], [FlightName], [fromCity], [toCity], [mileAge], [departureTime]) VALUES (N'CA1947', N'3         ', N'空客340', N'上海        ', N'成都        ', N'2800', N'08:03:00.0000000')
GO

INSERT INTO [dbo].[Flight] ([FlightNumber], [companyID], [FlightName], [fromCity], [toCity], [mileAge], [departureTime]) VALUES (N'CZ3117', N'1         ', N'波音738', N'武汉        ', N'北京        ', N'1100', N'08:06:00.0000000')
GO

INSERT INTO [dbo].[Flight] ([FlightNumber], [companyID], [FlightName], [fromCity], [toCity], [mileAge], [departureTime]) VALUES (N'CZ3907', N'1         ', N'空客333', N'北京        ', N'上海        ', N'1130', N'18:05:00.0000000')
GO

INSERT INTO [dbo].[Flight] ([FlightNumber], [companyID], [FlightName], [fromCity], [toCity], [mileAge], [departureTime]) VALUES (N'CZ6356', N'1         ', N'波音738', N'海口        ', N'郑州        ', N'1873', N'11:51:00.0000000')
GO

INSERT INTO [dbo].[Flight] ([FlightNumber], [companyID], [FlightName], [fromCity], [toCity], [mileAge], [departureTime]) VALUES (N'CZ6553', N'1         ', N'空客320', N'长春        ', N'上海        ', N'1698', N'16:30:00.0000000')
GO

INSERT INTO [dbo].[Flight] ([FlightNumber], [companyID], [FlightName], [fromCity], [toCity], [mileAge], [departureTime]) VALUES (N'HO1252', N'7         ', N'空客320', N'北京        ', N'上海        ', N'1130', N'06:40:00.0000000')
GO

INSERT INTO [dbo].[Flight] ([FlightNumber], [companyID], [FlightName], [fromCity], [toCity], [mileAge], [departureTime]) VALUES (N'HO1284', N'7         ', N'空客320', N'长春        ', N'上海        ', N'1698', N'14:45:00.0000000')
GO

INSERT INTO [dbo].[Flight] ([FlightNumber], [companyID], [FlightName], [fromCity], [toCity], [mileAge], [departureTime]) VALUES (N'HU7188', N'4         ', N'波音738', N'武汉        ', N'北京        ', N'1100', N'15:55:00.0000000')
GO

INSERT INTO [dbo].[Flight] ([FlightNumber], [companyID], [FlightName], [fromCity], [toCity], [mileAge], [departureTime]) VALUES (N'HU7291', N'4         ', N'波音738', N'海口        ', N'郑州        ', N'1873', N'13:05:00.0000000')
GO

INSERT INTO [dbo].[Flight] ([FlightNumber], [companyID], [FlightName], [fromCity], [toCity], [mileAge], [departureTime]) VALUES (N'HU7309', N'4         ', N'波音738', N'海口        ', N'郑州        ', N'1873', N'08:31:00.0000000')
GO

INSERT INTO [dbo].[Flight] ([FlightNumber], [companyID], [FlightName], [fromCity], [toCity], [mileAge], [departureTime]) VALUES (N'MF8069', N'6         ', N'波音738', N'南宁        ', N'沈阳        ', N'2780', N'08:03:00.0000000')
GO

INSERT INTO [dbo].[Flight] ([FlightNumber], [companyID], [FlightName], [fromCity], [toCity], [mileAge], [departureTime]) VALUES (N'MF8073', N'6         ', N'波音738', N'厦门        ', N'沈阳        ', N'2242', N'07:41:00.0000000')
GO

INSERT INTO [dbo].[Flight] ([FlightNumber], [companyID], [FlightName], [fromCity], [toCity], [mileAge], [departureTime]) VALUES (N'MU2451', N'2         ', N'波音738', N'武汉        ', N'北京        ', N'1100', N'09:04:00.0000000')
GO

INSERT INTO [dbo].[Flight] ([FlightNumber], [companyID], [FlightName], [fromCity], [toCity], [mileAge], [departureTime]) VALUES (N'MU2453', N'2         ', N'波音738', N'武汉        ', N'北京        ', N'1100', N'15:00:00.0000000')
GO

INSERT INTO [dbo].[Flight] ([FlightNumber], [companyID], [FlightName], [fromCity], [toCity], [mileAge], [departureTime]) VALUES (N'MU2540', N'2         ', N'波音738', N'上海        ', N'成都        ', N'2800', N'19:30:00.0000000')
GO

INSERT INTO [dbo].[Flight] ([FlightNumber], [companyID], [FlightName], [fromCity], [toCity], [mileAge], [departureTime]) VALUES (N'MU2885', N'2         ', N'空客320', N'南京        ', N'西安        ', N'1104', N'19:50:00.0000000')
GO

INSERT INTO [dbo].[Flight] ([FlightNumber], [companyID], [FlightName], [fromCity], [toCity], [mileAge], [departureTime]) VALUES (N'MU294', N'2         ', N'空客320', N'上海        ', N'成都        ', N'2800', N'11:15:00.0000000')
GO

INSERT INTO [dbo].[Flight] ([FlightNumber], [companyID], [FlightName], [fromCity], [toCity], [mileAge], [departureTime]) VALUES (N'MU5102', N'2         ', N'空客333', N'北京        ', N'上海        ', N'1130', N'08:39:00.0000000')
GO

INSERT INTO [dbo].[Flight] ([FlightNumber], [companyID], [FlightName], [fromCity], [toCity], [mileAge], [departureTime]) VALUES (N'MU5680', N'2         ', N'空客332', N'长春        ', N'上海        ', N'1698', N'13:50:00.0000000')
GO

INSERT INTO [dbo].[Flight] ([FlightNumber], [companyID], [FlightName], [fromCity], [toCity], [mileAge], [departureTime]) VALUES (N'SC1191', N'9         ', N'波音738', N'济南        ', N'昆明        ', N'2080', N'20:00:00.0000000')
GO

INSERT INTO [dbo].[Flight] ([FlightNumber], [companyID], [FlightName], [fromCity], [toCity], [mileAge], [departureTime]) VALUES (N'SC4873', N'9         ', N'波音738', N'济南        ', N'昆明        ', N'2080', N'08:45:00.0000000')
GO

INSERT INTO [dbo].[Flight] ([FlightNumber], [companyID], [FlightName], [fromCity], [toCity], [mileAge], [departureTime]) VALUES (N'ZH9438', N'5         ', N'空客320', N'海口        ', N'郑州        ', N'1873', N'17:00:00.0000000')
GO

INSERT INTO [dbo].[Flight] ([FlightNumber], [companyID], [FlightName], [fromCity], [toCity], [mileAge], [departureTime]) VALUES (N'ZH9516', N'5         ', N'空客320', N'厦门        ', N'沈阳        ', N'2242', N'16:20:00.0000000')
GO

INSERT INTO [dbo].[Flight] ([FlightNumber], [companyID], [FlightName], [fromCity], [toCity], [mileAge], [departureTime]) VALUES (N'ZH9602', N'5         ', N'空客320', N'厦门        ', N'沈阳        ', N'2242', N'14:55:00.0000000')
GO


-- ----------------------------
-- Table structure for Passenger
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[Passenger]') AND type IN ('U'))
	DROP TABLE [dbo].[Passenger]
GO

CREATE TABLE [dbo].[Passenger] (
  [PassengerIdentity] varchar(50) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [PName] varchar(50) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [gender] varchar(50) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [birthday] varchar(50) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [PTele] varchar(50) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [IdentityStyle] varchar(50) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [PaymentState] varchar(50) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [PTicketNumber] varchar(20) COLLATE Chinese_PRC_CI_AS  NOT NULL
)
GO

ALTER TABLE [dbo].[Passenger] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of Passenger
-- ----------------------------
INSERT INTO [dbo].[Passenger] ([PassengerIdentity], [PName], [gender], [birthday], [PTele], [IdentityStyle], [PaymentState], [PTicketNumber]) VALUES (N'100506198304161675', N'苏地方', N'男', N'1983-04-16', N'13867886598', N'护照', N'1', N'4')
GO

INSERT INTO [dbo].[Passenger] ([PassengerIdentity], [PName], [gender], [birthday], [PTele], [IdentityStyle], [PaymentState], [PTicketNumber]) VALUES (N'100712197303186681', N'额外可', N'女', N'1973-03-18', N'15367211029', N'护照', N'1', N'3')
GO

INSERT INTO [dbo].[Passenger] ([PassengerIdentity], [PName], [gender], [birthday], [PTele], [IdentityStyle], [PaymentState], [PTicketNumber]) VALUES (N'101009197404104775', N'委任为', N'男', N'1974-04-10', N'13578983521', N'护照', N'1', N'2')
GO

INSERT INTO [dbo].[Passenger] ([PassengerIdentity], [PName], [gender], [birthday], [PTele], [IdentityStyle], [PaymentState], [PTicketNumber]) VALUES (N'280103198309125341', N'刘亚蒙', N'男', N'1976-10-20', N'13565888845', N'大陆居民身份证', N'1', N'2 ')
GO

INSERT INTO [dbo].[Passenger] ([PassengerIdentity], [PName], [gender], [birthday], [PTele], [IdentityStyle], [PaymentState], [PTicketNumber]) VALUES (N'280103199806195832', N'郝琼琼', N'女', N'1987-03-12', N'15334564321', N'大陆居民身份证', N'1', N'1')
GO

INSERT INTO [dbo].[Passenger] ([PassengerIdentity], [PName], [gender], [birthday], [PTele], [IdentityStyle], [PaymentState], [PTicketNumber]) VALUES (N'280104198703125883', N'雷亚波', N'男', N'1988-06-19', N'13686035678', N'大陆居民身份证', N'1', N'2')
GO

INSERT INTO [dbo].[Passenger] ([PassengerIdentity], [PName], [gender], [birthday], [PTele], [IdentityStyle], [PaymentState], [PTicketNumber]) VALUES (N'280105197610200914', N'魏国兰', N'女', N'1983-09-12', N'13827653456', N'大陆居民身份证', N'1', N'5')
GO

INSERT INTO [dbo].[Passenger] ([PassengerIdentity], [PName], [gender], [birthday], [PTele], [IdentityStyle], [PaymentState], [PTicketNumber]) VALUES (N'640104198703125881', N'他亚波', N'男', N'1988-06-19', N'13686035678', N'港澳通行证', N'1', N'1')
GO

INSERT INTO [dbo].[Passenger] ([PassengerIdentity], [PName], [gender], [birthday], [PTele], [IdentityStyle], [PaymentState], [PTicketNumber]) VALUES (N'640105197610200916', N'贴国兰', N'女', N'1983-09-12', N'13827653456', N'港澳通行证', N'1', N'2')
GO

INSERT INTO [dbo].[Passenger] ([PassengerIdentity], [PName], [gender], [birthday], [PTele], [IdentityStyle], [PaymentState], [PTicketNumber]) VALUES (N'770103198309125344', N'万亚蒙', N'男', N'1976-10-20', N'13565888845', N'大陆居民身份证', N'1', N'1')
GO

INSERT INTO [dbo].[Passenger] ([PassengerIdentity], [PName], [gender], [birthday], [PTele], [IdentityStyle], [PaymentState], [PTicketNumber]) VALUES (N'770103199806195830', N'任琼琼', N'女', N'1987-03-12', N'15334564321', N'港澳通行证', N'1', N'2')
GO

INSERT INTO [dbo].[Passenger] ([PassengerIdentity], [PName], [gender], [birthday], [PTele], [IdentityStyle], [PaymentState], [PTicketNumber]) VALUES (N'770106199208113735', N'李慧娟', N'女', N'1992-08-11', N'13967341256', N'大陆居民身份证', N'1', N'6')
GO

INSERT INTO [dbo].[Passenger] ([PassengerIdentity], [PName], [gender], [birthday], [PTele], [IdentityStyle], [PaymentState], [PTicketNumber]) VALUES (N'770107198307762086', N'吕兰梦', N'女', N'1983-04-26', N'13878292910', N'大陆居民身份证', N'1', N'1')
GO

INSERT INTO [dbo].[Passenger] ([PassengerIdentity], [PName], [gender], [birthday], [PTele], [IdentityStyle], [PaymentState], [PTicketNumber]) VALUES (N'770211197905122417', N'郝嘉志', N'男', N'1979-05-12', N'15945673771', N'大陆居民身份证', N'1', N'2')
GO

INSERT INTO [dbo].[Passenger] ([PassengerIdentity], [PName], [gender], [birthday], [PTele], [IdentityStyle], [PaymentState], [PTicketNumber]) VALUES (N'770506198304161678', N'苏彦博', N'男', N'1983-04-16', N'13867886598', N'大陆居民身份证', N'1', N'2')
GO

INSERT INTO [dbo].[Passenger] ([PassengerIdentity], [PName], [gender], [birthday], [PTele], [IdentityStyle], [PaymentState], [PTicketNumber]) VALUES (N'770712197303186689', N'严雅可', N'女', N'1973-03-18', N'15367217729', N'大陆居民身份证', N'1', N'5')
GO

INSERT INTO [dbo].[Passenger] ([PassengerIdentity], [PName], [gender], [birthday], [PTele], [IdentityStyle], [PaymentState], [PTicketNumber]) VALUES (N'771009197404104770', N'傅明远', N'男', N'1974-04-10', N'13578983521', N'大陆居民身份证', N'1', N'3')
GO

INSERT INTO [dbo].[Passenger] ([PassengerIdentity], [PName], [gender], [birthday], [PTele], [IdentityStyle], [PaymentState], [PTicketNumber]) VALUES (N'800211197905122415', N'郝大纲', N'男', N'1979-05-12', N'15945673801', N'护照', N'1', N'5')
GO

INSERT INTO [dbo].[Passenger] ([PassengerIdentity], [PName], [gender], [birthday], [PTele], [IdentityStyle], [PaymentState], [PTicketNumber]) VALUES (N'820106199208113738', N'发慧娟', N'女', N'1992-08-11', N'13967341256', N'港澳通行证', N'1', N'3')
GO

INSERT INTO [dbo].[Passenger] ([PassengerIdentity], [PName], [gender], [birthday], [PTele], [IdentityStyle], [PaymentState], [PTicketNumber]) VALUES (N'820107198308062089', N'才兰梦', N'女', N'1983-04-26', N'13878292910', N'港澳通行证', N'1', N'5')
GO


-- ----------------------------
-- Table structure for Ticket
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[Ticket]') AND type IN ('U'))
	DROP TABLE [dbo].[Ticket]
GO

CREATE TABLE [dbo].[Ticket] (
  [TicketNumber] varchar(20) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [Price] int  NULL,
  [discount] float(53)  NULL,
  [Condition] varchar(10) COLLATE Chinese_PRC_CI_AS  NULL,
  [worker] varchar(10) COLLATE Chinese_PRC_CI_AS  NULL,
  [TflightNumber] varchar(10) COLLATE Chinese_PRC_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[Ticket] SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'预售状态',
'SCHEMA', N'dbo',
'TABLE', N'Ticket',
'COLUMN', N'Condition'
GO

EXEC sp_addextendedproperty
'MS_Description', N'经受业务员',
'SCHEMA', N'dbo',
'TABLE', N'Ticket',
'COLUMN', N'worker'
GO


-- ----------------------------
-- Records of Ticket
-- ----------------------------
INSERT INTO [dbo].[Ticket] ([TicketNumber], [Price], [discount], [Condition], [worker], [TflightNumber]) VALUES (N'1         ', N'3245', N'0.4', N'1', N'1', N'3U8948')
GO

INSERT INTO [dbo].[Ticket] ([TicketNumber], [Price], [discount], [Condition], [worker], [TflightNumber]) VALUES (N'10        ', N'312', N'0.3', N'0', N'4', N'MF8073')
GO

INSERT INTO [dbo].[Ticket] ([TicketNumber], [Price], [discount], [Condition], [worker], [TflightNumber]) VALUES (N'11        ', N'453', N'0.2', N'0', N'4', N'MU2885')
GO

INSERT INTO [dbo].[Ticket] ([TicketNumber], [Price], [discount], [Condition], [worker], [TflightNumber]) VALUES (N'12        ', N'312', N'0.6', N'0', N'4', N'MU2885')
GO

INSERT INTO [dbo].[Ticket] ([TicketNumber], [Price], [discount], [Condition], [worker], [TflightNumber]) VALUES (N'13        ', N'546', N'0.3', N'0', N'4', N'MU294')
GO

INSERT INTO [dbo].[Ticket] ([TicketNumber], [Price], [discount], [Condition], [worker], [TflightNumber]) VALUES (N'2         ', N'3252', N'0.8', N'1', N'1', N'3U8948')
GO

INSERT INTO [dbo].[Ticket] ([TicketNumber], [Price], [discount], [Condition], [worker], [TflightNumber]) VALUES (N'3         ', N'6588', N'0.5', N'1', N'1', N'HO1284')
GO

INSERT INTO [dbo].[Ticket] ([TicketNumber], [Price], [discount], [Condition], [worker], [TflightNumber]) VALUES (N'4         ', N'2356', N'0.4', N'1', N'1', N'HO1284')
GO

INSERT INTO [dbo].[Ticket] ([TicketNumber], [Price], [discount], [Condition], [worker], [TflightNumber]) VALUES (N'5         ', N'6484', N'0.6', N'1', N'3', N'CZ6356')
GO

INSERT INTO [dbo].[Ticket] ([TicketNumber], [Price], [discount], [Condition], [worker], [TflightNumber]) VALUES (N'6         ', N'25476', N'0.7', N'1', N'3', N'HU7188')
GO

INSERT INTO [dbo].[Ticket] ([TicketNumber], [Price], [discount], [Condition], [worker], [TflightNumber]) VALUES (N'7         ', N'2466', N'0.4', N'0', N'3', N'HU7188')
GO

INSERT INTO [dbo].[Ticket] ([TicketNumber], [Price], [discount], [Condition], [worker], [TflightNumber]) VALUES (N'8         ', N'352', N'0.4', N'0', N'3', N'HU7188')
GO

INSERT INTO [dbo].[Ticket] ([TicketNumber], [Price], [discount], [Condition], [worker], [TflightNumber]) VALUES (N'9         ', N'535', N'0.2', N'0', N'3', N'MF8073')
GO


-- ----------------------------
-- View structure for FlightFromBeijing
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[FlightFromBeijing]') AND type IN ('V'))
	DROP VIEW [dbo].[FlightFromBeijing]
GO

CREATE VIEW [dbo].[FlightFromBeijing] AS SELECT FlightName,fromCity
FROM flight
WHERE fromcity='北京'
GO


-- ----------------------------
-- Primary Key structure for table Company
-- ----------------------------
ALTER TABLE [dbo].[Company] ADD CONSTRAINT [PK_Company] PRIMARY KEY CLUSTERED ([CompanyNumber])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Triggers structure for table Flight
-- ----------------------------
CREATE TRIGGER [dbo].[trig_insert]
ON [dbo].[Flight]
WITH EXECUTE AS CALLER
FOR INSERT
AS
begin
    if object_id(N'Flight_sum',N'U') is null--判断Flight_sum表是否存在
        create table Flight_sum(FlightCount int default(0));--创建存储学生人数的Flight_sum表
    declare @FliNumber int;
    select @FliNumber = count(*)from Flight;
    if not exists (select * from Flight)--判断表中是否有记录
        insert into Flight_sum values(0);
    update Flight_sum set FlightCount =@FliNumber; --把更新后总的学生数插入到Flight_sum表中
end
GO


-- ----------------------------
-- Primary Key structure for table Flight
-- ----------------------------
ALTER TABLE [dbo].[Flight] ADD CONSTRAINT [PK__Flight__2EAE6F51B04BC0EC] PRIMARY KEY CLUSTERED ([FlightNumber])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Primary Key structure for table Passenger
-- ----------------------------
ALTER TABLE [dbo].[Passenger] ADD CONSTRAINT [PK_Passenger] PRIMARY KEY CLUSTERED ([PassengerIdentity])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Primary Key structure for table Ticket
-- ----------------------------
ALTER TABLE [dbo].[Ticket] ADD CONSTRAINT [PK_Ticket] PRIMARY KEY CLUSTERED ([TicketNumber])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Foreign Keys structure for table Passenger
-- ----------------------------
ALTER TABLE [dbo].[Passenger] ADD CONSTRAINT [FK_Passenger_Ticket] FOREIGN KEY ([PTicketNumber]) REFERENCES [dbo].[Ticket] ([TicketNumber]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO


-- ----------------------------
-- Foreign Keys structure for table Ticket
-- ----------------------------
ALTER TABLE [dbo].[Ticket] ADD CONSTRAINT [FK_Ticket_Flight] FOREIGN KEY ([TflightNumber]) REFERENCES [dbo].[Flight] ([FlightNumber]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO

