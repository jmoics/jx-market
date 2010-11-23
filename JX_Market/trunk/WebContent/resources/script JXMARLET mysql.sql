-- MySQL dump 10.13  Distrib 5.1.41, for debian-linux-gnu (i486)
--
-- Host: localhost    Database: jxmarket
-- ------------------------------------------------------
-- Server version	5.1.41-3ubuntu12.7

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `T_EMP_AREA`
--

DROP TABLE IF EXISTS `T_EMP_AREA`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_EMP_AREA` (
  `AREA_N_CODIGO` int(11) NOT NULL AUTO_INCREMENT,
  `EMPRESA_N_CODIGO` int(11) NOT NULL,
  `AREA_V_NOMBRE` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`AREA_N_CODIGO`),
  KEY `FK_REL_EMPRESA_AREA` (`EMPRESA_N_CODIGO`),
  CONSTRAINT `FK_REL_EMPRESA_AREA` FOREIGN KEY (`EMPRESA_N_CODIGO`) REFERENCES `T_EMP_EMPRESA` (`EMPRESA_N_CODIGO`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_EMP_AREA`
--

LOCK TABLES `T_EMP_AREA` WRITE;
/*!40000 ALTER TABLE `T_EMP_AREA` DISABLE KEYS */;
INSERT INTO `T_EMP_AREA` VALUES (1,2,'Gerencia'),(2,2,'Ventas'),(3,2,'Productos');
/*!40000 ALTER TABLE `T_EMP_AREA` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `T_EMP_EMPLEADO`
--

DROP TABLE IF EXISTS `T_EMP_EMPLEADO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_EMP_EMPLEADO` (
  `EMPLEADO_N_CODIGO` int(11) NOT NULL AUTO_INCREMENT,
  `PERFIL_N_CODIGO` int(11) NOT NULL,
  `USUARIO_N_CODIGO` int(11) NOT NULL,
  `EMPRESA_N_CODIGO` int(11) NOT NULL,
  `EMPLEADO_V_NOMBRE` varchar(50) DEFAULT NULL,
  `EMPLEADO_V_APELLIDO` varchar(50) DEFAULT NULL,
  `EMPLEADO_V_DNI` varchar(15) DEFAULT NULL,
  `EMPLEADO_V_DIRECCION` varchar(70) DEFAULT NULL,
  `EMPLEADO_V_TELEFONO` varchar(15) DEFAULT NULL,
  `EMPLEADO_V_CELULAR` varchar(20) DEFAULT NULL,
  `EMPLEADO_V_EMAIL` varchar(30) DEFAULT NULL,
  `EMPLEADO_V_CIUDAD` varchar(20) DEFAULT NULL,
  `EMPLEADO_V_REGION` varchar(20) DEFAULT NULL,
  `EMPLEADO_D_FECHNAC` date DEFAULT NULL,
  `EMPLEADO_N_ACTIVO` int(11) DEFAULT NULL,
  PRIMARY KEY (`EMPLEADO_N_CODIGO`),
  KEY `FK_REL_EMPLEADO_PERFIL` (`PERFIL_N_CODIGO`),
  KEY `FK_REL_EMPLEADO_USUARIO` (`USUARIO_N_CODIGO`),
  KEY `FK_REL_EMPRESA_EMPLEADO` (`EMPRESA_N_CODIGO`),
  CONSTRAINT `FK_REL_EMPLEADO_PERFIL` FOREIGN KEY (`PERFIL_N_CODIGO`) REFERENCES `T_SEG_PERFIL` (`PERFIL_N_CODIGO`),
  CONSTRAINT `FK_REL_EMPLEADO_USUARIO` FOREIGN KEY (`USUARIO_N_CODIGO`) REFERENCES `T_SEG_USUARIO` (`USUARIO_N_CODIGO`),
  CONSTRAINT `FK_REL_EMPRESA_EMPLEADO` FOREIGN KEY (`EMPRESA_N_CODIGO`) REFERENCES `T_EMP_EMPRESA` (`EMPRESA_N_CODIGO`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_EMP_EMPLEADO`
--

LOCK TABLES `T_EMP_EMPLEADO` WRITE;
/*!40000 ALTER TABLE `T_EMP_EMPLEADO` DISABLE KEYS */;
INSERT INTO `T_EMP_EMPLEADO` VALUES (1,1,1,2,'Jorge','Cueva','44128463','Mi casa','4823262','945190242','jmoics@gmail.com','Lima','Lima','1987-12-01',1);
/*!40000 ALTER TABLE `T_EMP_EMPLEADO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `T_EMP_EMPRESA`
--

DROP TABLE IF EXISTS `T_EMP_EMPRESA`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_EMP_EMPRESA` (
  `EMPRESA_N_CODIGO` int(11) NOT NULL AUTO_INCREMENT,
  `EMPRESA_V_RAZONSOCIAL` varchar(200) DEFAULT NULL,
  `EMPRESA_N_ACTIVO` int(11) DEFAULT NULL,
  `EMPRESA_V_RUC` varchar(11) DEFAULT NULL,
  `EMPRESA_V_DOMINIO` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`EMPRESA_N_CODIGO`),
  UNIQUE KEY `EMPRESA_V_DOMINIO_UNIQUE` (`EMPRESA_V_DOMINIO`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_EMP_EMPRESA`
--

LOCK TABLES `T_EMP_EMPRESA` WRITE;
/*!40000 ALTER TABLE `T_EMP_EMPRESA` DISABLE KEYS */;
INSERT INTO `T_EMP_EMPRESA` VALUES (1,'JX_Market',1,'44563458976','jxmarket'),(2,'Metro',1,'35346576899','metro'),(3,'moxter.net',1,'56452345346','moxternet');
/*!40000 ALTER TABLE `T_EMP_EMPRESA` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `T_NEG_ARTICULO`
--

DROP TABLE IF EXISTS `T_NEG_ARTICULO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_NEG_ARTICULO` (
  `ARTICULO_N_CODIGO` int(11) NOT NULL AUTO_INCREMENT,
  `EMPRESA_N_CODIGO` int(11) NOT NULL,
  `CATEGORIA_N_CODIGO` int(11) NOT NULL,
  `ARTICULO_V_NOMBRE` varchar(70) DEFAULT NULL,
  `ARTICULO_V_MARCA` varchar(30) DEFAULT NULL,
  `ARTICULO_V_DESCRIPCION` varchar(4000) DEFAULT NULL,
  `ARTICULO_N_PRECIO` float(8,2) DEFAULT NULL,
  `ARTICULO_N_STOCK` int(11) DEFAULT '0',
  `ARTICULO_N_ACTIVO` int(11) DEFAULT NULL,
  `ARTICULO_V_IMAGEN` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ARTICULO_N_CODIGO`),
  KEY `FK_REL_ARTICULO_TIPO` (`CATEGORIA_N_CODIGO`),
  KEY `FK_REL_EMPRESA_ARTICULO` (`EMPRESA_N_CODIGO`),
  CONSTRAINT `FK_REL_ARTICULO_TIPO` FOREIGN KEY (`CATEGORIA_N_CODIGO`) REFERENCES `T_NEG_CATEGORIA` (`CATEGORIA_N_CODIGO`),
  CONSTRAINT `FK_REL_EMPRESA_ARTICULO` FOREIGN KEY (`EMPRESA_N_CODIGO`) REFERENCES `T_EMP_EMPRESA` (`EMPRESA_N_CODIGO`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_NEG_ARTICULO`
--

LOCK TABLES `T_NEG_ARTICULO` WRITE;
/*!40000 ALTER TABLE `T_NEG_ARTICULO` DISABLE KEYS */;
INSERT INTO `T_NEG_ARTICULO` VALUES (1,2,1,'Ring Action','Capcom','juego de video de accion basado en la trilogia del senior de los anillos',24.50,20,1,NULL),(2,2,1,'Final Fantasy X','Sony','Juego de ROL, para consola Play Station 2',25.90,NULL,1,NULL),(11,2,3,'PS2','Sony','play station 2 color azul con un mando y una memory card',550.00,NULL,1,'2.1.PS2.22589'),(12,2,3,'PS2','Sony','play station 2 azul y negro mas un mando',540.00,NULL,1,'2.1.PS2.938193'),(13,3,1,'Final fantasy x-2','Eydos','Juego rpg final fantasy x-2 original en caja',35.00,NULL,1,'3.1.Final fantasy x-2.950467'),(14,3,1,'Final Fantasy XII','Eydos','Juego Final Fantasy XII original algo rayadito XD',40.00,NULL,1,'3.1.Final Fantasy XII.180339'),(15,3,3,'PS2','Sony','consola ps2',500.00,NULL,1,'3.3.PS2.918564');
/*!40000 ALTER TABLE `T_NEG_ARTICULO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `T_NEG_CATEGORIA`
--

DROP TABLE IF EXISTS `T_NEG_CATEGORIA`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_NEG_CATEGORIA` (
  `CATEGORIA_N_CODIGO` int(11) NOT NULL AUTO_INCREMENT,
  `EMPRESA_N_CODIGO` int(11) NOT NULL,
  `CATEGORIA_V_NOMBRE` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`CATEGORIA_N_CODIGO`),
  KEY `FK_REL_EMPRESA_CATEGORIA` (`EMPRESA_N_CODIGO`),
  CONSTRAINT `FK_REL_EMPRESA_CATEGORIA` FOREIGN KEY (`EMPRESA_N_CODIGO`) REFERENCES `T_EMP_EMPRESA` (`EMPRESA_N_CODIGO`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_NEG_CATEGORIA`
--

LOCK TABLES `T_NEG_CATEGORIA` WRITE;
/*!40000 ALTER TABLE `T_NEG_CATEGORIA` DISABLE KEYS */;
INSERT INTO `T_NEG_CATEGORIA` VALUES (1,1,'Juegos'),(2,1,'Libros'),(3,1,'Consola');
/*!40000 ALTER TABLE `T_NEG_CATEGORIA` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `T_NEG_CLIENTE`
--

DROP TABLE IF EXISTS `T_NEG_CLIENTE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_NEG_CLIENTE` (
  `CLIENTE_N_CODIGO` int(11) NOT NULL,
  `EMPRESA_N_CODIGO` int(11) NOT NULL,
  `USUARIO_N_CODIGO` int(11) NOT NULL,
  `CLIENTE_V_NOMBRE` varchar(60) DEFAULT NULL,
  `CLIENTE_V_APELLIDO` varchar(60) DEFAULT NULL,
  `CLIENTE_D_FECHANAC` date DEFAULT NULL,
  `CLIENTE_N_DNI` int(11) DEFAULT NULL,
  `CLIENTE_V_DIRECCION` varchar(80) DEFAULT NULL,
  `CLIENTE_V_TELEFONO` smallint(6) DEFAULT NULL,
  `CLIENTE_V_CELULAR` smallint(6) DEFAULT NULL,
  `CLIENTE_V_EMAIL` varchar(30) DEFAULT NULL,
  `CLIENTE_V_CIUDAD` varchar(20) DEFAULT NULL,
  `CLIENTE_V_REGION` varchar(20) DEFAULT NULL,
  `CLIENTE_V_CODPOSTAL` smallint(6) DEFAULT NULL,
  `CLIENTE_N_ACTIVO` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`CLIENTE_N_CODIGO`),
  KEY `FK_REL_CLIENTE_USUARIO` (`USUARIO_N_CODIGO`),
  KEY `FK_REL_EMPRESA_CLIENTE` (`EMPRESA_N_CODIGO`),
  CONSTRAINT `FK_REL_CLIENTE_USUARIO` FOREIGN KEY (`USUARIO_N_CODIGO`) REFERENCES `T_SEG_USUARIO` (`USUARIO_N_CODIGO`),
  CONSTRAINT `FK_REL_EMPRESA_CLIENTE` FOREIGN KEY (`EMPRESA_N_CODIGO`) REFERENCES `T_EMP_EMPRESA` (`EMPRESA_N_CODIGO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_NEG_CLIENTE`
--

LOCK TABLES `T_NEG_CLIENTE` WRITE;
/*!40000 ALTER TABLE `T_NEG_CLIENTE` DISABLE KEYS */;
/*!40000 ALTER TABLE `T_NEG_CLIENTE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `T_NEG_PEDIDO`
--

DROP TABLE IF EXISTS `T_NEG_PEDIDO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_NEG_PEDIDO` (
  `PEDIDO_N_CODIGO` int(11) NOT NULL,
  `EMPRESA_N_CODIGO` int(11) NOT NULL,
  `CLIENTE_N_CODIGO` int(11) NOT NULL,
  `TIPOPED_N_CODIGO` int(11) NOT NULL,
  `PEDIDO_D_FECHA` date DEFAULT NULL,
  `PEDIDO_N_IGV` int(11) DEFAULT NULL,
  `PEDIDO_N_TOTAL` float(8,2) DEFAULT NULL,
  PRIMARY KEY (`PEDIDO_N_CODIGO`),
  KEY `FK_REL_EMPRESA_PEDIDO` (`EMPRESA_N_CODIGO`),
  KEY `FK_REL_PEDIDO_CLIENTE` (`CLIENTE_N_CODIGO`),
  KEY `FK_REL_PEDIDO_TIPO` (`TIPOPED_N_CODIGO`),
  CONSTRAINT `FK_REL_EMPRESA_PEDIDO` FOREIGN KEY (`EMPRESA_N_CODIGO`) REFERENCES `T_EMP_EMPRESA` (`EMPRESA_N_CODIGO`),
  CONSTRAINT `FK_REL_PEDIDO_CLIENTE` FOREIGN KEY (`CLIENTE_N_CODIGO`) REFERENCES `T_NEG_CLIENTE` (`CLIENTE_N_CODIGO`),
  CONSTRAINT `FK_REL_PEDIDO_TIPO` FOREIGN KEY (`TIPOPED_N_CODIGO`) REFERENCES `T_NEG_PEDIDO_TIPO` (`TIPOPED_N_CODIGO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_NEG_PEDIDO`
--

LOCK TABLES `T_NEG_PEDIDO` WRITE;
/*!40000 ALTER TABLE `T_NEG_PEDIDO` DISABLE KEYS */;
/*!40000 ALTER TABLE `T_NEG_PEDIDO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `T_NEG_PEDIDO_DET`
--

DROP TABLE IF EXISTS `T_NEG_PEDIDO_DET`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_NEG_PEDIDO_DET` (
  `PEDIDO_N_CODIGO` int(11) NOT NULL,
  `ARTICULO_N_CODIGO` int(11) NOT NULL,
  `DETPED_N_CANTIDAD` int(11) DEFAULT NULL,
  PRIMARY KEY (`PEDIDO_N_CODIGO`,`ARTICULO_N_CODIGO`),
  KEY `FK_T_NEG_PEDIDO_DET2` (`ARTICULO_N_CODIGO`),
  CONSTRAINT `FK_T_NEG_PEDIDO_DET` FOREIGN KEY (`PEDIDO_N_CODIGO`) REFERENCES `T_NEG_PEDIDO` (`PEDIDO_N_CODIGO`),
  CONSTRAINT `FK_T_NEG_PEDIDO_DET2` FOREIGN KEY (`ARTICULO_N_CODIGO`) REFERENCES `T_NEG_ARTICULO` (`ARTICULO_N_CODIGO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_NEG_PEDIDO_DET`
--

LOCK TABLES `T_NEG_PEDIDO_DET` WRITE;
/*!40000 ALTER TABLE `T_NEG_PEDIDO_DET` DISABLE KEYS */;
/*!40000 ALTER TABLE `T_NEG_PEDIDO_DET` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `T_NEG_PEDIDO_TIPO`
--

DROP TABLE IF EXISTS `T_NEG_PEDIDO_TIPO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_NEG_PEDIDO_TIPO` (
  `TIPOPED_N_CODIGO` int(11) NOT NULL,
  `TIPOPED_V_DESCRIPCION` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`TIPOPED_N_CODIGO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_NEG_PEDIDO_TIPO`
--

LOCK TABLES `T_NEG_PEDIDO_TIPO` WRITE;
/*!40000 ALTER TABLE `T_NEG_PEDIDO_TIPO` DISABLE KEYS */;
/*!40000 ALTER TABLE `T_NEG_PEDIDO_TIPO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `T_NEG_VENTA`
--

DROP TABLE IF EXISTS `T_NEG_VENTA`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_NEG_VENTA` (
  `VENTA_N_CODIGO` int(11) NOT NULL,
  `EMPRESA_N_CODIGO` int(11) NOT NULL,
  `TIPOVEN_N_CODIGO` int(11) NOT NULL,
  `PEDIDO_N_CODIGO` int(11) NOT NULL,
  `VENTA_D_FECHA` date DEFAULT NULL,
  `VENTA_N_IGV` int(11) DEFAULT NULL,
  `VENTA_VENTA_N_TOTAL` float(8,2) DEFAULT NULL,
  PRIMARY KEY (`VENTA_N_CODIGO`),
  KEY `FK_REL_EMPRESA_VENTA` (`EMPRESA_N_CODIGO`),
  KEY `FK_REL_VENTA_PEDIDO` (`PEDIDO_N_CODIGO`),
  KEY `FK_REL_VENTA_TIPO` (`TIPOVEN_N_CODIGO`),
  CONSTRAINT `FK_REL_EMPRESA_VENTA` FOREIGN KEY (`EMPRESA_N_CODIGO`) REFERENCES `T_EMP_EMPRESA` (`EMPRESA_N_CODIGO`),
  CONSTRAINT `FK_REL_VENTA_PEDIDO` FOREIGN KEY (`PEDIDO_N_CODIGO`) REFERENCES `T_NEG_PEDIDO` (`PEDIDO_N_CODIGO`),
  CONSTRAINT `FK_REL_VENTA_TIPO` FOREIGN KEY (`TIPOVEN_N_CODIGO`) REFERENCES `T_NEG_VENTA_TIPO` (`TIPOVEN_N_CODIGO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_NEG_VENTA`
--

LOCK TABLES `T_NEG_VENTA` WRITE;
/*!40000 ALTER TABLE `T_NEG_VENTA` DISABLE KEYS */;
/*!40000 ALTER TABLE `T_NEG_VENTA` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `T_NEG_VENTA_TIPO`
--

DROP TABLE IF EXISTS `T_NEG_VENTA_TIPO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_NEG_VENTA_TIPO` (
  `TIPOVEN_N_CODIGO` int(11) NOT NULL,
  `EMPRESA_N_CODIGO` int(11) NOT NULL,
  `TIPOVEN_V_DESCRIPCION` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`TIPOVEN_N_CODIGO`),
  KEY `FK_REL_EMPRESA_TIPOVENTA` (`EMPRESA_N_CODIGO`),
  CONSTRAINT `FK_REL_EMPRESA_TIPOVENTA` FOREIGN KEY (`EMPRESA_N_CODIGO`) REFERENCES `T_EMP_EMPRESA` (`EMPRESA_N_CODIGO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_NEG_VENTA_TIPO`
--

LOCK TABLES `T_NEG_VENTA_TIPO` WRITE;
/*!40000 ALTER TABLE `T_NEG_VENTA_TIPO` DISABLE KEYS */;
/*!40000 ALTER TABLE `T_NEG_VENTA_TIPO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `T_SEG_MODULO`
--

DROP TABLE IF EXISTS `T_SEG_MODULO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_SEG_MODULO` (
  `MODULO_ID` float NOT NULL,
  `EMPRESA_N_CODIGO` int(11) NOT NULL,
  `T_S_MODULO_ID` float NOT NULL,
  `MODULO_DESC` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`MODULO_ID`),
  KEY `FK_REL_EMPRESA_MODULO` (`EMPRESA_N_CODIGO`),
  KEY `FK_REL_MODULO_MODULO` (`T_S_MODULO_ID`),
  CONSTRAINT `FK_REL_EMPRESA_MODULO` FOREIGN KEY (`EMPRESA_N_CODIGO`) REFERENCES `T_EMP_EMPRESA` (`EMPRESA_N_CODIGO`),
  CONSTRAINT `FK_REL_MODULO_MODULO` FOREIGN KEY (`T_S_MODULO_ID`) REFERENCES `T_SEG_MODULO` (`MODULO_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_SEG_MODULO`
--

LOCK TABLES `T_SEG_MODULO` WRITE;
/*!40000 ALTER TABLE `T_SEG_MODULO` DISABLE KEYS */;
/*!40000 ALTER TABLE `T_SEG_MODULO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `T_SEG_MOD_DET`
--

DROP TABLE IF EXISTS `T_SEG_MOD_DET`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_SEG_MOD_DET` (
  `PERFIL_N_CODIGO` int(11) NOT NULL,
  `MODULO_ID` float NOT NULL,
  `ROL_N_ID` int(11) NOT NULL,
  `MODDET_N_HABILITADO` int(11) DEFAULT NULL,
  PRIMARY KEY (`PERFIL_N_CODIGO`,`MODULO_ID`,`ROL_N_ID`),
  KEY `FK_T_SEG_MOD_DET2` (`MODULO_ID`),
  KEY `FK_T_SEG_MOD_DET3` (`ROL_N_ID`),
  CONSTRAINT `FK_T_SEG_MOD_DET` FOREIGN KEY (`PERFIL_N_CODIGO`) REFERENCES `T_SEG_PERFIL` (`PERFIL_N_CODIGO`),
  CONSTRAINT `FK_T_SEG_MOD_DET2` FOREIGN KEY (`MODULO_ID`) REFERENCES `T_SEG_MODULO` (`MODULO_ID`),
  CONSTRAINT `FK_T_SEG_MOD_DET3` FOREIGN KEY (`ROL_N_ID`) REFERENCES `T_SEG_ROL` (`ROL_N_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_SEG_MOD_DET`
--

LOCK TABLES `T_SEG_MOD_DET` WRITE;
/*!40000 ALTER TABLE `T_SEG_MOD_DET` DISABLE KEYS */;
/*!40000 ALTER TABLE `T_SEG_MOD_DET` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `T_SEG_PERFIL`
--

DROP TABLE IF EXISTS `T_SEG_PERFIL`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_SEG_PERFIL` (
  `PERFIL_N_CODIGO` int(11) NOT NULL AUTO_INCREMENT,
  `EMPRESA_N_CODIGO` int(11) NOT NULL,
  `AREA_N_CODIGO` int(11) NOT NULL,
  `PERFIL_V_FUNCION` varchar(50) DEFAULT NULL,
  `PERFIL_V_DESCRIPCION` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`PERFIL_N_CODIGO`),
  KEY `FK_REL_EMPRESA_PERFIL` (`EMPRESA_N_CODIGO`),
  KEY `FK_REL_PERFIL_AREA` (`AREA_N_CODIGO`),
  CONSTRAINT `FK_REL_EMPRESA_PERFIL` FOREIGN KEY (`EMPRESA_N_CODIGO`) REFERENCES `T_EMP_EMPRESA` (`EMPRESA_N_CODIGO`),
  CONSTRAINT `FK_REL_PERFIL_AREA` FOREIGN KEY (`AREA_N_CODIGO`) REFERENCES `T_EMP_AREA` (`AREA_N_CODIGO`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_SEG_PERFIL`
--

LOCK TABLES `T_SEG_PERFIL` WRITE;
/*!40000 ALTER TABLE `T_SEG_PERFIL` DISABLE KEYS */;
INSERT INTO `T_SEG_PERFIL` VALUES (1,2,1,'Administracion','Administracion General');
/*!40000 ALTER TABLE `T_SEG_PERFIL` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `T_SEG_ROL`
--

DROP TABLE IF EXISTS `T_SEG_ROL`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_SEG_ROL` (
  `ROL_N_ID` int(11) NOT NULL,
  `EMPRESA_N_CODIGO` int(11) NOT NULL,
  `ROL_V_DESCRIPCION` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ROL_N_ID`),
  KEY `FK_REL_EMPRESA_ROL` (`EMPRESA_N_CODIGO`),
  CONSTRAINT `FK_REL_EMPRESA_ROL` FOREIGN KEY (`EMPRESA_N_CODIGO`) REFERENCES `T_EMP_EMPRESA` (`EMPRESA_N_CODIGO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_SEG_ROL`
--

LOCK TABLES `T_SEG_ROL` WRITE;
/*!40000 ALTER TABLE `T_SEG_ROL` DISABLE KEYS */;
/*!40000 ALTER TABLE `T_SEG_ROL` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `T_SEG_ROL_DET`
--

DROP TABLE IF EXISTS `T_SEG_ROL_DET`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_SEG_ROL_DET` (
  `ROL_N_ID` int(11) NOT NULL,
  `USUARIO_N_CODIGO` int(11) NOT NULL,
  PRIMARY KEY (`ROL_N_ID`,`USUARIO_N_CODIGO`),
  KEY `FK_T_SEG_ROL_DET2` (`USUARIO_N_CODIGO`),
  CONSTRAINT `FK_T_SEG_ROL_DET` FOREIGN KEY (`ROL_N_ID`) REFERENCES `T_SEG_ROL` (`ROL_N_ID`),
  CONSTRAINT `FK_T_SEG_ROL_DET2` FOREIGN KEY (`USUARIO_N_CODIGO`) REFERENCES `T_SEG_USUARIO` (`USUARIO_N_CODIGO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_SEG_ROL_DET`
--

LOCK TABLES `T_SEG_ROL_DET` WRITE;
/*!40000 ALTER TABLE `T_SEG_ROL_DET` DISABLE KEYS */;
/*!40000 ALTER TABLE `T_SEG_ROL_DET` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `T_SEG_USUARIO`
--

DROP TABLE IF EXISTS `T_SEG_USUARIO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_SEG_USUARIO` (
  `USUARIO_N_CODIGO` int(11) NOT NULL AUTO_INCREMENT,
  `EMPRESA_N_CODIGO` int(11) NOT NULL,
  `USUARIO_V_CONTRASENA` varchar(150) DEFAULT NULL,
  `USUARIO_V_USERNAME` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`USUARIO_N_CODIGO`),
  KEY `FK_REL_EMPRESA_USUARIO` (`EMPRESA_N_CODIGO`),
  CONSTRAINT `FK_REL_EMPRESA_USUARIO` FOREIGN KEY (`EMPRESA_N_CODIGO`) REFERENCES `T_EMP_EMPRESA` (`EMPRESA_N_CODIGO`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_SEG_USUARIO`
--

LOCK TABLES `T_SEG_USUARIO` WRITE;
/*!40000 ALTER TABLE `T_SEG_USUARIO` DISABLE KEYS */;
INSERT INTO `T_SEG_USUARIO` VALUES (1,2,'8679b5756696bc0e69308525b298f27b','jmoics'),(2,3,'552fea927d2ca4f8736fd19f5e50eee4','jcueva');
/*!40000 ALTER TABLE `T_SEG_USUARIO` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2010-11-22 21:51:14