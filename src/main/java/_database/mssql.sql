/****** Object:  StoredProcedure [dbo].[sp_OutputAndReturn]    Script Date: 07/10/2015 10:35:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[sp_OutputAndReturn]
@Input1 int,
@Input2 varchar(30),
@Input3 datetime,
@Output1 int output,
@Output2 varchar(30) output,
@Output3 datetime output
AS
BEGIN
	set @Output1=@Input1+1
	set @Output2=@Input2
	set @Output3=@Input3
	return 999
END
GO
/****** Object:  Table [dbo].[App]    Script Date: 07/10/2015 10:35:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[App](
	[AppId] [int] IDENTITY(1,1) NOT NULL,
	[AppName] [varchar](50) NULL,
	[AppType] [int] NULL,
	[AppDesc] [varchar](200) NULL,
	[IOSDownLink] [varchar](100) NULL,
	[AndroidDownLink] [varchar](100) NULL,
	[SecretKey] [varchar](32) NULL,
	[Status] [int] NULL,
	[AddTime] [datetime] NULL,
	[OperatorId] [int] NULL,
 CONSTRAINT [PK_App] PRIMARY KEY CLUSTERED 
(
	[AppId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[App] ON
INSERT [dbo].[App] ([AppId], [AppName], [AppType], [AppDesc], [IOSDownLink], [AndroidDownLink], [SecretKey], [Status], [AddTime], [OperatorId]) VALUES (1, N'≤‚ ‘', 1, N'≤‚ ‘App', N'', N'', N'9b46f7aa44976215c257398b94803fb8', 100, CAST(0x0000A4A5014D406D AS DateTime), 0)
INSERT [dbo].[App] ([AppId], [AppName], [AppType], [AppDesc], [IOSDownLink], [AndroidDownLink], [SecretKey], [Status], [AddTime], [OperatorId]) VALUES (2, N'≤‚ ‘1', 1, N'≤‚ ‘1', NULL, NULL, N'9b46f7aa44976215c257398b94803fb8', 99, CAST(0x0000A4CE00C0628E AS DateTime), 0)
INSERT [dbo].[App] ([AppId], [AppName], [AppType], [AppDesc], [IOSDownLink], [AndroidDownLink], [SecretKey], [Status], [AddTime], [OperatorId]) VALUES (3, N'≤‚ ‘22', 0, N'≤‚ ‘22', N'111', N'222', N'9b46f7aa44976215c257398b94803fb8', 99, CAST(0x0000A4CE00C06293 AS DateTime), 0)
INSERT [dbo].[App] ([AppId], [AppName], [AppType], [AppDesc], [IOSDownLink], [AndroidDownLink], [SecretKey], [Status], [AddTime], [OperatorId]) VALUES (4, N'≤‚ ‘33', 1, N'≤‚ ‘3', N'', N'', N'9b46f7aa44976215c257398b94803fb8', 99, CAST(0x0000A4CE00C06293 AS DateTime), 0)
INSERT [dbo].[App] ([AppId], [AppName], [AppType], [AppDesc], [IOSDownLink], [AndroidDownLink], [SecretKey], [Status], [AddTime], [OperatorId]) VALUES (5, N'≤‚ ‘4', 1, N'≤‚ ‘4', NULL, NULL, N'9b46f7aa44976215c257398b94803fb8', 1, CAST(0x0000A4CE00C06293 AS DateTime), NULL)
INSERT [dbo].[App] ([AppId], [AppName], [AppType], [AppDesc], [IOSDownLink], [AndroidDownLink], [SecretKey], [Status], [AddTime], [OperatorId]) VALUES (6, N'≤‚ ‘5', 1, N'≤‚ ‘5', NULL, NULL, N'9b46f7aa44976215c257398b94803fb8', 1, CAST(0x0000A4CE00C06293 AS DateTime), 0)
INSERT [dbo].[App] ([AppId], [AppName], [AppType], [AppDesc], [IOSDownLink], [AndroidDownLink], [SecretKey], [Status], [AddTime], [OperatorId]) VALUES (7, N'≤‚ ‘6', 1, N'≤‚ ‘6', NULL, NULL, N'9b46f7aa44976215c257398b94803fb8', 1, CAST(0x0000A4CE00C06295 AS DateTime), 0)
INSERT [dbo].[App] ([AppId], [AppName], [AppType], [AppDesc], [IOSDownLink], [AndroidDownLink], [SecretKey], [Status], [AddTime], [OperatorId]) VALUES (8, N'≤‚ ‘7', 1, N'≤‚ ‘7', NULL, NULL, N'9b46f7aa44976215c257398b94803fb8', 1, CAST(0x0000A4CE00C06296 AS DateTime), 0)
INSERT [dbo].[App] ([AppId], [AppName], [AppType], [AppDesc], [IOSDownLink], [AndroidDownLink], [SecretKey], [Status], [AddTime], [OperatorId]) VALUES (9, N'≤‚ ‘8', 1, N'≤‚ ‘8', NULL, NULL, N'9b46f7aa44976215c257398b94803fb8', 1, CAST(0x0000A4CE00C06296 AS DateTime), 0)
INSERT [dbo].[App] ([AppId], [AppName], [AppType], [AppDesc], [IOSDownLink], [AndroidDownLink], [SecretKey], [Status], [AddTime], [OperatorId]) VALUES (10, N'–¬APP', 0, N'–¬APP', N'–¬APP1', N'–¬APP2', N'', 1, CAST(0x0000A4CF00C57E7B AS DateTime), 0)
INSERT [dbo].[App] ([AppId], [AppName], [AppType], [AppDesc], [IOSDownLink], [AndroidDownLink], [SecretKey], [Status], [AddTime], [OperatorId]) VALUES (11, N'123', 1, N'123', N'123', N'123', N'', 1, CAST(0x0000A4CF00C67834 AS DateTime), 0)
INSERT [dbo].[App] ([AppId], [AppName], [AppType], [AppDesc], [IOSDownLink], [AndroidDownLink], [SecretKey], [Status], [AddTime], [OperatorId]) VALUES (12, N'456', 1, N'123', N'123', N'123', N'', 1, CAST(0x0000A4CF00E02807 AS DateTime), 0)
INSERT [dbo].[App] ([AppId], [AppName], [AppType], [AppDesc], [IOSDownLink], [AndroidDownLink], [SecretKey], [Status], [AddTime], [OperatorId]) VALUES (13, N'test 111', 0, N'test 111', N'test 111', N'test 111', N'test 111', 1, CAST(0x0000A4CF014B6E88 AS DateTime), 0)
INSERT [dbo].[App] ([AppId], [AppName], [AppType], [AppDesc], [IOSDownLink], [AndroidDownLink], [SecretKey], [Status], [AddTime], [OperatorId]) VALUES (14, N'test 222', 0, N'test 222', N'test 222', N'test 222', N'test 222', 1, CAST(0x0000A4CF014B8B24 AS DateTime), 0)
INSERT [dbo].[App] ([AppId], [AppName], [AppType], [AppDesc], [IOSDownLink], [AndroidDownLink], [SecretKey], [Status], [AddTime], [OperatorId]) VALUES (15, N'test 333', 0, N'test 333', N'test 333', N'test 333', N'test 333', 1, CAST(0x0000A4CF014BDA78 AS DateTime), 0)
INSERT [dbo].[App] ([AppId], [AppName], [AppType], [AppDesc], [IOSDownLink], [AndroidDownLink], [SecretKey], [Status], [AddTime], [OperatorId]) VALUES (16, N'test 444', 0, N'test 444', N'test 444', N'test 444', N'test 444', 99, CAST(0x0000A4CF014BE99C AS DateTime), 0)
INSERT [dbo].[App] ([AppId], [AppName], [AppType], [AppDesc], [IOSDownLink], [AndroidDownLink], [SecretKey], [Status], [AddTime], [OperatorId]) VALUES (17, N'test 555', 0, N'test 555', N'test 555', N'test 555', N'test 555', 99, CAST(0x0000A4CF014C36CF AS DateTime), 0)
SET IDENTITY_INSERT [dbo].[App] OFF
/****** Object:  StoredProcedure [dbo].[sp_OutputAndSelect]    Script Date: 07/10/2015 10:35:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[sp_OutputAndSelect]
@Input1 int,
@Output1 int output
AS
BEGIN
	set @Output1=@Input1+1
	select * from App with(nolock)
END
GO
/****** Object:  Default [DF_App_Status]    Script Date: 07/10/2015 10:35:17 ******/
ALTER TABLE [dbo].[App] ADD  CONSTRAINT [DF_App_Status]  DEFAULT ((1)) FOR [Status]
GO
/****** Object:  Default [DF_App_AddTime]    Script Date: 07/10/2015 10:35:17 ******/
ALTER TABLE [dbo].[App] ADD  CONSTRAINT [DF_App_AddTime]  DEFAULT (getdate()) FOR [AddTime]
GO
