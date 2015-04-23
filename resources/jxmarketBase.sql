-- MySQL dump 10.13  Distrib 5.1.41, for debian-linux-gnu (i486)
--
-- Host: localhost    Database: jxmarket
-- ------------------------------------------------------
-- Server version	5.1.41-3ubuntu12.8

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
  `COMPANY_N_CODIGO` int(11) NOT NULL,
  `AREA_V_NOMBRE` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`AREA_N_CODIGO`),
  KEY `FK_REL_COMPANY_AREA` (`COMPANY_N_CODIGO`),
  CONSTRAINT `FK_REL_COMPANY_AREA` FOREIGN KEY (`COMPANY_N_CODIGO`) REFERENCES `T_COM_COMPANY` (`COMPANY_N_CODIGO`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_EMP_AREA`
--

LOCK TABLES `T_EMP_AREA` WRITE;
/*!40000 ALTER TABLE `T_EMP_AREA` DISABLE KEYS */;
INSERT INTO `T_EMP_AREA` VALUES (1,1,'Administration');
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
  `ROLE_N_CODIGO` int(11) NOT NULL,
  `USUARIO_N_CODIGO` int(11) NOT NULL,
  `COMPANY_N_CODIGO` int(11) NOT NULL,
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
  KEY `FK_REL_EMPLEADO_ROLE` (`ROLE_N_CODIGO`),
  KEY `FK_REL_EMPLEADO_USUARIO` (`USUARIO_N_CODIGO`),
  KEY `FK_REL_COMPANY_EMPLEADO` (`COMPANY_N_CODIGO`),
  CONSTRAINT `FK_REL_EMPLEADO_ROLE` FOREIGN KEY (`ROLE_N_CODIGO`) REFERENCES `T_SEG_ROLE` (`ROLE_N_CODIGO`),
  CONSTRAINT `FK_REL_EMPLEADO_USUARIO` FOREIGN KEY (`USUARIO_N_CODIGO`) REFERENCES `T_SEG_USUARIO` (`USUARIO_N_CODIGO`),
  CONSTRAINT `FK_REL_COMPANY_EMPLEADO` FOREIGN KEY (`COMPANY_N_CODIGO`) REFERENCES `T_COM_COMPANY` (`COMPANY_N_CODIGO`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_EMP_EMPLEADO`
--

LOCK TABLES `T_EMP_EMPLEADO` WRITE;
/*!40000 ALTER TABLE `T_EMP_EMPLEADO` DISABLE KEYS */;
INSERT INTO `T_EMP_EMPLEADO` VALUES (1,1,1,1,'JX_Market','Administrator','','','','','jxmarket@gmail.com','Lima','Lima','01-01-01',1);
/*!40000 ALTER TABLE `T_EMP_EMPLEADO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `T_COM_COMPANY`
--

DROP TABLE IF EXISTS `T_COM_COMPANY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_COM_COMPANY` (
  `COMPANY_N_CODIGO` int(11) NOT NULL AUTO_INCREMENT,
  `COMPANY_V_RAZONSOCIAL` varchar(200) DEFAULT NULL,
  `COMPANY_N_ACTIVO` int(11) DEFAULT NULL,
  `COMPANY_V_RUC` varchar(11) DEFAULT NULL,
  `COMPANY_V_DOMINIO` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`COMPANY_N_CODIGO`),
  UNIQUE KEY `COMPANY_V_DOMINIO_UNIQUE` (`COMPANY_V_DOMINIO`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_COM_COMPANY`
--

LOCK TABLES `T_COM_COMPANY` WRITE;
/*!40000 ALTER TABLE `T_COM_COMPANY` DISABLE KEYS */;
INSERT INTO `T_COM_COMPANY` VALUES (1,'JX_Market',1,'44563458976','jxmarket');
/*!40000 ALTER TABLE `T_COM_COMPANY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `T_BUS_ARTICULO`
--

DROP TABLE IF EXISTS `T_BUS_ARTICULO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_BUS_ARTICULO` (
  `ARTICULO_N_CODIGO` int(11) NOT NULL AUTO_INCREMENT,
  `COMPANY_N_CODIGO` int(11) NOT NULL,
  `CATEGORY_N_CODIGO` int(11) NOT NULL,
  `ARTICULO_V_NOMBRE` varchar(70) DEFAULT NULL,
  `ARTICULO_V_MARCA` varchar(30) DEFAULT NULL,
  `ARTICULO_V_DESCRIPCION` varchar(4000) DEFAULT NULL,
  `ARTICULO_N_PRECIO` float(8,2) DEFAULT NULL,
  `ARTICULO_N_STOCK` int(11) DEFAULT '0',
  `ARTICULO_N_ACTIVO` int(11) DEFAULT NULL,
  `ARTICULO_V_IMAGEN` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ARTICULO_N_CODIGO`),
  KEY `FK_REL_ARTICULO_TIPO` (`CATEGORY_N_CODIGO`),
  KEY `FK_REL_COMPANY_ARTICULO` (`COMPANY_N_CODIGO`),
  CONSTRAINT `FK_REL_ARTICULO_TIPO` FOREIGN KEY (`CATEGORY_N_CODIGO`) REFERENCES `T_BUS_CATEGORY` (`CATEGORY_N_CODIGO`),
  CONSTRAINT `FK_REL_COMPANY_ARTICULO` FOREIGN KEY (`COMPANY_N_CODIGO`) REFERENCES `T_COM_COMPANY` (`COMPANY_N_CODIGO`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_BUS_ARTICULO`
--

LOCK TABLES `T_BUS_ARTICULO` WRITE;
/*!40000 ALTER TABLE `T_BUS_ARTICULO` DISABLE KEYS */;
/*!40000 ALTER TABLE `T_BUS_ARTICULO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `T_BUS_CATEGORY`
--

DROP TABLE IF EXISTS `T_BUS_CATEGORY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_BUS_CATEGORY` (
  `CATEGORY_N_CODIGO` int(11) NOT NULL AUTO_INCREMENT,
  `COMPANY_N_CODIGO` int(11) NOT NULL,
  `CATEGORY_V_NOMBRE` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`CATEGORY_N_CODIGO`),
  KEY `FK_REL_COMPANY_CATEGORY` (`COMPANY_N_CODIGO`),
  CONSTRAINT `FK_REL_COMPANY_CATEGORY` FOREIGN KEY (`COMPANY_N_CODIGO`) REFERENCES `T_COM_COMPANY` (`COMPANY_N_CODIGO`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_BUS_CATEGORY`
--

LOCK TABLES `T_BUS_CATEGORY` WRITE;
/*!40000 ALTER TABLE `T_BUS_CATEGORY` DISABLE KEYS */;
INSERT INTO `T_BUS_CATEGORY` VALUES (1,1,'Descarga Digital'),(2,1,'Libros'),(3,1,'Consolas Videojuegos'),(4,1,'Bebidas'),(5,1,'Peliculas, Musica y Juegos'),(6,1,'Computadoras y Oficina'),(7,1,'Electronica'),(8,1,'Casa, Jardin y mascotas'),(9,1,'Juguetes, Ninhos y Bebes'),(10,1,'Ropa, zapatos y joyeria'),(11,1,'Deportes'),(12,1,'Herramientas'),(13,1,'Automoviles');
/*!40000 ALTER TABLE `T_BUS_CATEGORY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `T_BUS_CLIENT`
--

DROP TABLE IF EXISTS `T_BUS_CLIENT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_BUS_CLIENT` (
  `CLIENTE_N_CODIGO` int(11) NOT NULL AUTO_INCREMENT,
  `COMPANY_N_CODIGO` int(11) NOT NULL,
  `USUARIO_N_CODIGO` int(11) NOT NULL,
  `CLIENTE_V_NOMBRE` varchar(60) DEFAULT NULL,
  `CLIENTE_V_APELLIDO` varchar(60) DEFAULT NULL,
  `CLIENTE_D_FECHANAC` date DEFAULT NULL,
  `CLIENTE_N_DNI` int(11) DEFAULT NULL,
  `CLIENTE_V_DIRECCION` varchar(80) DEFAULT NULL,
  `CLIENTE_V_TELEFONO` varchar(10) DEFAULT NULL,
  `CLIENTE_V_CELULAR` varchar(10) DEFAULT NULL,
  `CLIENTE_V_EMAIL` varchar(30) DEFAULT NULL,
  `CLIENTE_V_CIUDAD` varchar(20) DEFAULT NULL,
  `CLIENTE_V_REGION` varchar(20) DEFAULT NULL,
  `CLIENTE_V_CODPOSTAL` smallint(6) DEFAULT NULL,
  `CLIENTE_N_ACTIVO` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`CLIENTE_N_CODIGO`),
  KEY `FK_REL_CLIENTE_USUARIO` (`USUARIO_N_CODIGO`),
  KEY `FK_REL_COMPANY_CLIENTE` (`COMPANY_N_CODIGO`),
  CONSTRAINT `FK_REL_CLIENTE_USUARIO` FOREIGN KEY (`USUARIO_N_CODIGO`) REFERENCES `T_SEG_USUARIO` (`USUARIO_N_CODIGO`),
  CONSTRAINT `FK_REL_COMPANY_CLIENTE` FOREIGN KEY (`COMPANY_N_CODIGO`) REFERENCES `T_COM_COMPANY` (`COMPANY_N_CODIGO`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_BUS_CLIENT`
--

LOCK TABLES `T_BUS_CLIENT` WRITE;
/*!40000 ALTER TABLE `T_BUS_CLIENT` DISABLE KEYS */;
/*!40000 ALTER TABLE `T_BUS_CLIENT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `T_BUS_PEDIDO`
--

DROP TABLE IF EXISTS `T_BUS_PEDIDO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_BUS_PEDIDO` (
  `PEDIDO_N_CODIGO` int(11) NOT NULL AUTO_INCREMENT,
  `COMPANY_N_CODIGO` int(11) NOT NULL,
  `CLIENTE_N_CODIGO` int(11) NOT NULL,
  `TIPOPED_N_CODIGO` int(11) NOT NULL,
  `PEDIDO_D_FECHA` date DEFAULT NULL,
  `PEDIDO_N_IGV` int(11) DEFAULT NULL,
  `PEDIDO_N_TOTAL` float(8,2) DEFAULT NULL,
  PRIMARY KEY (`PEDIDO_N_CODIGO`),
  KEY `FK_REL_COMPANY_PEDIDO` (`COMPANY_N_CODIGO`),
  KEY `FK_REL_PEDIDO_CLIENTE` (`CLIENTE_N_CODIGO`),
  KEY `FK_REL_PEDIDO_TIPO` (`TIPOPED_N_CODIGO`),
  CONSTRAINT `FK_REL_COMPANY_PEDIDO` FOREIGN KEY (`COMPANY_N_CODIGO`) REFERENCES `T_COM_COMPANY` (`COMPANY_N_CODIGO`),
  CONSTRAINT `FK_REL_PEDIDO_CLIENTE` FOREIGN KEY (`CLIENTE_N_CODIGO`) REFERENCES `T_BUS_CLIENT` (`CLIENTE_N_CODIGO`),
  CONSTRAINT `FK_REL_PEDIDO_TIPO` FOREIGN KEY (`TIPOPED_N_CODIGO`) REFERENCES `T_BUS_PEDIDO_TIPO` (`TIPOPED_N_CODIGO`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_BUS_PEDIDO`
--

LOCK TABLES `T_BUS_PEDIDO` WRITE;
/*!40000 ALTER TABLE `T_BUS_PEDIDO` DISABLE KEYS */;
/*!40000 ALTER TABLE `T_BUS_PEDIDO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `T_BUS_PEDIDO2PEDIDO`
--

DROP TABLE IF EXISTS `T_BUS_PEDIDO2PEDIDO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_BUS_PEDIDO2PEDIDO` (
  `PEDIDO_N_CODIGO_FROM` int(11) NOT NULL,
  `PEDIDO_N_CODIGO_TO` int(11) NOT NULL,
  PRIMARY KEY (`PEDIDO_N_CODIGO_FROM`,`PEDIDO_N_CODIGO_TO`),
  KEY `fk_T_BUS_PEDIDO2PEDIDO_1` (`PEDIDO_N_CODIGO_FROM`),
  KEY `fk_T_BUS_PEDIDO2PEDIDO_2` (`PEDIDO_N_CODIGO_TO`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_BUS_PEDIDO2PEDIDO`
--

LOCK TABLES `T_BUS_PEDIDO2PEDIDO` WRITE;
/*!40000 ALTER TABLE `T_BUS_PEDIDO2PEDIDO` DISABLE KEYS */;
/*!40000 ALTER TABLE `T_BUS_PEDIDO2PEDIDO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `T_BUS_PEDIDO_DET`
--

DROP TABLE IF EXISTS `T_BUS_PEDIDO_DET`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_BUS_PEDIDO_DET` (
  `PEDIDO_N_CODIGO` int(11) NOT NULL,
  `ARTICULO_N_CODIGO` int(11) NOT NULL,
  `DETPED_N_CANTIDAD` int(11) DEFAULT NULL,
  PRIMARY KEY (`PEDIDO_N_CODIGO`,`ARTICULO_N_CODIGO`),
  KEY `FK_T_BUS_PEDIDO_DET2` (`ARTICULO_N_CODIGO`),
  CONSTRAINT `FK_T_BUS_PEDIDO_DET` FOREIGN KEY (`PEDIDO_N_CODIGO`) REFERENCES `T_BUS_PEDIDO` (`PEDIDO_N_CODIGO`),
  CONSTRAINT `FK_T_BUS_PEDIDO_DET2` FOREIGN KEY (`ARTICULO_N_CODIGO`) REFERENCES `T_BUS_ARTICULO` (`ARTICULO_N_CODIGO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_BUS_PEDIDO_DET`
--

LOCK TABLES `T_BUS_PEDIDO_DET` WRITE;
/*!40000 ALTER TABLE `T_BUS_PEDIDO_DET` DISABLE KEYS */;
/*!40000 ALTER TABLE `T_BUS_PEDIDO_DET` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `T_BUS_PEDIDO_TIPO`
--

DROP TABLE IF EXISTS `T_BUS_PEDIDO_TIPO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_BUS_PEDIDO_TIPO` (
  `TIPOPED_N_CODIGO` int(11) NOT NULL AUTO_INCREMENT,
  `TIPOPED_V_DESCRIPCION` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`TIPOPED_N_CODIGO`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_BUS_PEDIDO_TIPO`
--

LOCK TABLES `T_BUS_PEDIDO_TIPO` WRITE;
/*!40000 ALTER TABLE `T_BUS_PEDIDO_TIPO` DISABLE KEYS */;
INSERT INTO `T_BUS_PEDIDO_TIPO` VALUES (1,'Entrega a domicilio'),(2,'Recoger el producto');
/*!40000 ALTER TABLE `T_BUS_PEDIDO_TIPO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `T_BUS_SOLICITUD`
--

DROP TABLE IF EXISTS `T_BUS_SOLICITUD`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_BUS_SOLICITUD` (
  `SOLICITUD_N_CODIGO` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `SOLICITUD_V_RAZON` varchar(100) DEFAULT NULL,
  `SOLICITUD_N_RUC` varchar(11) DEFAULT NULL,
  `SOLICITUD_D_FECHA` date DEFAULT NULL,
  `SOLICITUD_N_ESTADO` int(1) unsigned DEFAULT NULL,
  `SOLICITUD_V_EMAIL` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`SOLICITUD_N_CODIGO`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_BUS_SOLICITUD`
--

LOCK TABLES `T_BUS_SOLICITUD` WRITE;
/*!40000 ALTER TABLE `T_BUS_SOLICITUD` DISABLE KEYS */;
/*!40000 ALTER TABLE `T_BUS_SOLICITUD` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `T_SEG_MODULE`
--

DROP TABLE IF EXISTS `T_SEG_MODULE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_SEG_MODULE` (
  `MODULE_N_CODIGO` int(11) NOT NULL AUTO_INCREMENT,
  `COMPANY_N_CODIGO` int(11) NOT NULL,
  `MODULE_V_DESC` varchar(50) DEFAULT NULL,
  `MODULE_V_RECURSO` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`MODULE_N_CODIGO`),
  KEY `FK_REL_COMPANY_MODULE` (`COMPANY_N_CODIGO`),
  CONSTRAINT `FK_REL_COMPANY_MODULE` FOREIGN KEY (`COMPANY_N_CODIGO`) REFERENCES `T_COM_COMPANY` (`COMPANY_N_CODIGO`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_SEG_MODULE`
--

LOCK TABLES `T_SEG_MODULE` WRITE;
/*!40000 ALTER TABLE `T_SEG_MODULE` DISABLE KEYS */;
INSERT INTO `T_SEG_MODULE` VALUES (1,1,'Module para administracion','MODULE_ADMINISTRACION');
/*!40000 ALTER TABLE `T_SEG_MODULE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `T_SEG_ROLE2MODULE`
--

DROP TABLE IF EXISTS `T_SEG_ROLE2MODULE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_SEG_ROLE2MODULE` (
  `ROLE_N_CODIGO` int(11) NOT NULL,
  `MODULE_N_CODIGO` int(11) NOT NULL,
  `MODDET_N_HABILITADO` int(11) DEFAULT NULL,
  PRIMARY KEY (`ROLE_N_CODIGO`,`MODULE_N_CODIGO`),
  KEY `MODULE_N_CODIGO` (`MODULE_N_CODIGO`),
  KEY `FK_T_SEG_ROLE2MODULE_1` (`MODULE_N_CODIGO`),
  CONSTRAINT `FK_T_SEG_ROLE2MODULE` FOREIGN KEY (`ROLE_N_CODIGO`) REFERENCES `T_SEG_ROLE` (`ROLE_N_CODIGO`),
  CONSTRAINT `FK_T_SEG_ROLE2MODULE_1` FOREIGN KEY (`MODULE_N_CODIGO`) REFERENCES `T_SEG_MODULE` (`MODULE_N_CODIGO`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_SEG_ROLE2MODULE`
--

LOCK TABLES `T_SEG_ROLE2MODULE` WRITE;
/*!40000 ALTER TABLE `T_SEG_ROLE2MODULE` DISABLE KEYS */;
INSERT INTO `T_SEG_ROLE2MODULE` VALUES (1,1,NULL);
/*!40000 ALTER TABLE `T_SEG_ROLE2MODULE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `T_SEG_ROLE`
--

DROP TABLE IF EXISTS `T_SEG_ROLE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_SEG_ROLE` (
  `ROLE_N_CODIGO` int(11) NOT NULL AUTO_INCREMENT,
  `COMPANY_N_CODIGO` int(11) NOT NULL,
  `AREA_N_CODIGO` int(11) NOT NULL,
  `ROLE_V_FUNCION` varchar(50) DEFAULT NULL,
  `ROLE_V_DESCRIPCION` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`ROLE_N_CODIGO`),
  KEY `FK_REL_COMPANY_ROLE` (`COMPANY_N_CODIGO`),
  KEY `FK_REL_ROLE_AREA` (`AREA_N_CODIGO`),
  CONSTRAINT `FK_REL_COMPANY_ROLE` FOREIGN KEY (`COMPANY_N_CODIGO`) REFERENCES `T_COM_COMPANY` (`COMPANY_N_CODIGO`),
  CONSTRAINT `FK_REL_ROLE_AREA` FOREIGN KEY (`AREA_N_CODIGO`) REFERENCES `T_EMP_AREA` (`AREA_N_CODIGO`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_SEG_ROLE`
--

LOCK TABLES `T_SEG_ROLE` WRITE;
/*!40000 ALTER TABLE `T_SEG_ROLE` DISABLE KEYS */;
INSERT INTO `T_SEG_ROLE` VALUES (1,1,1,'Administrator','Administrator');
/*!40000 ALTER TABLE `T_SEG_ROLE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `T_SEG_USUARIO`
--

DROP TABLE IF EXISTS `T_SEG_USUARIO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_SEG_USUARIO` (
  `USUARIO_N_CODIGO` int(11) NOT NULL AUTO_INCREMENT,
  `COMPANY_N_CODIGO` int(11) NOT NULL,
  `USUARIO_V_CONTRASENA` varchar(150) DEFAULT NULL,
  `USUARIO_V_USERNAME` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`USUARIO_N_CODIGO`),
  KEY `FK_REL_COMPANY_USUARIO` (`COMPANY_N_CODIGO`),
  CONSTRAINT `FK_REL_COMPANY_USUARIO` FOREIGN KEY (`COMPANY_N_CODIGO`) REFERENCES `T_COM_COMPANY` (`COMPANY_N_CODIGO`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_SEG_USUARIO`
--

LOCK TABLES `T_SEG_USUARIO` WRITE;
/*!40000 ALTER TABLE `T_SEG_USUARIO` DISABLE KEYS */;
INSERT INTO `T_SEG_USUARIO` VALUES (1,1,'7b7bc2512ee1fedcd76bdc68926d4f7b','Administrator');
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

-- Dump completed on 2011-01-06  5:11:20
