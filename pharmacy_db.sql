-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- 主机： localhost
-- 生成日期： 2025-06-08 21:59:53
-- 服务器版本： 5.7.26
-- PHP 版本： 7.3.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 数据库： `pharmacy_db`
--

-- --------------------------------------------------------

--
-- 表的结构 `administrator`
--

CREATE TABLE `administrator` (
  `AdministratorID` int(11) NOT NULL,
  `Name` char(10) NOT NULL,
  `Gender` enum('男','女') DEFAULT '男',
  `Birthday` date DEFAULT NULL,
  `Phone` char(11) DEFAULT NULL,
  `Password` varchar(20) NOT NULL,
  `Rzrq` date NOT NULL,
  `Type` char(10) DEFAULT 'admin'
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- 转存表中的数据 `administrator`
--

INSERT INTO `administrator` (`AdministratorID`, `Name`, `Gender`, `Birthday`, `Phone`, `Password`, `Rzrq`, `Type`) VALUES
(1, 'admin', '男', NULL, NULL, 'admin', '2025-06-08', 'admin');

-- --------------------------------------------------------

--
-- 表的结构 `drug`
--

CREATE TABLE `drug` (
  `DrugID` int(11) NOT NULL,
  `Name` char(25) NOT NULL,
  `Inventory` char(8) NOT NULL,
  `Type` char(10) NOT NULL,
  `Expiry` int(5) NOT NULL,
  `Cost` decimal(5,2) NOT NULL,
  `Price` decimal(5,2) NOT NULL,
  `Supplier` char(10) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- 转存表中的数据 `drug`
--

INSERT INTO `drug` (`DrugID`, `Name`, `Inventory`, `Type`, `Expiry`, `Cost`, `Price`, `Supplier`) VALUES
(1, '阿莫西林胶囊', '143', '抗生素', 24, '8.50', '12.80', '石药集团'),
(2, '感冒灵胶囊', '198', '感冒药', 36, '6.20', '9.50', '三九药业'),
(3, '复方甘草片', '75', '止咳药', 24, '4.80', '7.20', '同仁堂'),
(4, '布洛芬缓释胶囊', '111', '消炎药', 36, '15.60', '22.50', '芬必得'),
(5, '黄连上清片', '171', '中成药', 24, '5.30', '8.00', '太极集团'),
(6, '硝苯地平缓释片', '87', '降压药', 36, '12.40', '18.60', '齐鲁制药'),
(7, '碘伏消毒液', '184', '外用药', 24, '3.50', '5.80', '云南白药'),
(8, '创可贴', '491', '医疗器械', 36, '0.80', '1.50', '强生'),
(9, '盐酸西替利嗪片', '53', '抗过敏药', 24, '10.20', '15.90', '仙特明'),
(10, '葡萄糖注射液', '31', '输液类', 24, '4.50', '6.80', '科伦药业');

-- --------------------------------------------------------

--
-- 表的结构 `order`
--

CREATE TABLE `order` (
  `Ddno` varchar(30) NOT NULL,
  `Date` date NOT NULL,
  `Hno` char(10) DEFAULT NULL,
  `Payment` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- 转存表中的数据 `order`
--

INSERT INTO `order` (`Ddno`, `Date`, `Hno`, `Payment`) VALUES
('2025060821372426', '2025-06-08', '2', '微信'),
('2025060821064522', '2025-06-08', '1', '微信'),
('2025060821444833', '2025-06-08', '1', '微信'),
('2025060821371279', '2025-06-08', '2', '微信'),
('2025060821373460', '2025-06-08', '2', '微信'),
('2025060821393528', '2025-06-08', '4', '微信'),
('2025060821442575', '2025-06-08', '1', '微信'),
('2025060821455962', '2025-06-08', '6', '微信');

-- --------------------------------------------------------

--
-- 表的结构 `user`
--

CREATE TABLE `user` (
  `UserID` int(11) NOT NULL,
  `Name` char(10) NOT NULL,
  `Gender` enum('男','女') DEFAULT '男',
  `Birthday` date DEFAULT NULL,
  `Phone` char(11) DEFAULT NULL,
  `Password` varchar(20) NOT NULL,
  `Note` char(50) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- 转存表中的数据 `user`
--

INSERT INTO `user` (`UserID`, `Name`, `Gender`, `Birthday`, `Phone`, `Password`, `Note`) VALUES
(1, 'sa', '女', '2003-09-11', '15516303444', 'sa', '我是第一个用户'),
(2, 'sa1', '男', NULL, NULL, 'sa1', NULL),
(3, 'sa2', '女', NULL, NULL, 'sa2', NULL),
(4, '来钱钱', '女', '2000-06-06', '12233445567', 'q', '0'),
(5, '的', '男', NULL, NULL, 'd', NULL),
(6, '发', '女', '2000-06-07', '12234564321', 'f', '而对方的');

--
-- 转储表的索引
--

--
-- 表的索引 `administrator`
--
ALTER TABLE `administrator`
  ADD PRIMARY KEY (`AdministratorID`),
  ADD UNIQUE KEY `Name` (`Name`),
  ADD UNIQUE KEY `Phone` (`Phone`);

--
-- 表的索引 `drug`
--
ALTER TABLE `drug`
  ADD PRIMARY KEY (`DrugID`);

--
-- 表的索引 `order`
--
ALTER TABLE `order`
  ADD PRIMARY KEY (`Ddno`),
  ADD KEY `Hno` (`Hno`);

--
-- 表的索引 `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`UserID`),
  ADD UNIQUE KEY `Name` (`Name`),
  ADD UNIQUE KEY `Phone` (`Phone`);

--
-- 在导出的表使用AUTO_INCREMENT
--

--
-- 使用表AUTO_INCREMENT `administrator`
--
ALTER TABLE `administrator`
  MODIFY `AdministratorID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- 使用表AUTO_INCREMENT `drug`
--
ALTER TABLE `drug`
  MODIFY `DrugID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- 使用表AUTO_INCREMENT `user`
--
ALTER TABLE `user`
  MODIFY `UserID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
