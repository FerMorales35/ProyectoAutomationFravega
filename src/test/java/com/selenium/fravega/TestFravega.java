package com.selenium.fravega;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.selenium.accesodato.ExcelUtils;
import com.selenium.driver.DriverFactory;
import com.selenium.pages.PageHomeBusquedaProducto;

public class TestFravega {
	
	WebDriver driver;
	String browser;
	String url;
	
	@BeforeMethod
	public void abrirBrowser(ITestContext context) {
		driver = DriverFactory.getBrowser(driver, context);
		browser = context.getCurrentXmlTest().getParameter("NombreNavegador").toLowerCase();
		url = context.getCurrentXmlTest().getParameter("url");

	}
	
	@DataProvider(name = "envioDatos")
	public Object[][] resultadoMarcaFiltrada() throws Exception {
		String excelPath = "src\\resources\\TestData.xlsx";
		String page = "Productos";
		ExcelUtils.setExcelFile(excelPath, page);
		int iTestCaseRow = ExcelUtils.getRowUsed();
		Object[][] testObjArray = ExcelUtils.getTableArray(excelPath, page, iTestCaseRow, 3);
		return (testObjArray);
	}
	
	@DataProvider(name = "envioDatos2")
	public Object[][] resultadoCantidadElementos() throws Exception {
		String excelPath = "src\\resources\\TestData.xlsx";
		String page = "Productos2";
		ExcelUtils.setExcelFile(excelPath, page);
		int iTestCaseRow = ExcelUtils.getRowUsed();
		Object[][] testObjArray = ExcelUtils.getTableArray(excelPath, page, iTestCaseRow, 3);
		return (testObjArray);
	}
	
	@DataProvider(name = "envioDatos3")
	public Object[][] resultadoBreadCrumb() throws Exception {
		String excelPath = "src\\resources\\TestData.xlsx";
		String page = "Productos3";
		ExcelUtils.setExcelFile(excelPath, page);
		int iTestCaseRow = ExcelUtils.getRowUsed();
		Object[][] testObjArray = ExcelUtils.getTableArray(excelPath, page, iTestCaseRow, 2);
		return (testObjArray);
	}

	@Test(priority = 1, dataProvider = "envioDatos" ,description = "Realizar una busqueda en el sitio web Fravega y validar que el titulo de cada elemento obtenido contenga la marca seleccionada")
	public void busquedaDeProducto(String busqueda,String tipoProducto,String buscarProducto) throws Exception {

		Reporter.log("Abrimos el navegador " + browser);
		Reporter.log("Nos dirigimos a " + url);
		PageHomeBusquedaProducto producto = PageFactory.initElements(driver, PageHomeBusquedaProducto.class);
		Thread.sleep(3000);
		producto.cerrarModalCodigoPostal();
		Thread.sleep(3000);
		Reporter.log("Ingresamos " + busqueda + " en la caja de busqueda");
		producto.ingresarArticuloparaBusqueda(busqueda);
		Thread.sleep(3000);
		Reporter.log("Seleccionamos " + tipoProducto + " de las opciones disponibles");
		producto.seleccionarTipoProducto(tipoProducto);
		Thread.sleep(3000);
		Reporter.log("Seleccionamos la marca " + buscarProducto + " de las opciones disponibles");
		producto.seleccionarMarca(buscarProducto);
		Thread.sleep(3000);
		Reporter.log("Validamos que cada uno de los elementos contengan la marca " + buscarProducto);
		Assert.assertEquals(producto.validarMarcaSeleccionada(buscarProducto), true);
	}

	@Test(priority = 2, dataProvider = "envioDatos2",description = "Filtrar por marca y validar la cantidad obtenida del producto")
	public void cantidadProductosXMarcaSeleccionada(String busqueda,String tipoProducto,String buscarProducto) throws Exception {

		Reporter.log("Abrimos el navegador " + browser);
		Reporter.log("Nos dirigimos a " + url);
		PageHomeBusquedaProducto producto = PageFactory.initElements(driver, PageHomeBusquedaProducto.class);
		Thread.sleep(3000);
		producto.cerrarModalCodigoPostal();
		Thread.sleep(3000);
		Reporter.log("Ingresamos " + busqueda + " en la caja de busqueda");
		producto.ingresarArticuloparaBusqueda(busqueda);
		Thread.sleep(3000);
		Reporter.log("Seleccionamos " + tipoProducto + " de las opciones disponibles");
		producto.seleccionarTipoProducto(tipoProducto);
		Reporter.log("Seleccionamos la marca " + buscarProducto + " de las opciones disponibles");
		producto.seleccionarMarca(buscarProducto);
		Thread.sleep(3000);
		Reporter.log("Validamos que el numero del resultado de la busqueda por marca se corresponda con la cantidad de elementos obtenidos");
        Reporter.log("Cantidad de resultados visualizados en el FE de la marca " + buscarProducto + " es " + producto.cantidadXMarca());
		Assert.assertEquals(producto.cantidadXMarca(), producto.validarCantidadDeLaMarcaSeleccionada(buscarProducto));
	}
	
	@Test(priority = 3, dataProvider = "envioDatos3",description = "Validar que el breadcrumb de la pagina se corresponda con el del producto ingresado" )
	public void validarBreadcrumbPaginaXproductoIngresado(String busqueda,String tipoProducto) throws Exception { 
		
		Reporter.log("Abrimos el navegador " + browser);
		Reporter.log("Nos dirigimos a " + url);
		PageHomeBusquedaProducto producto = PageFactory.initElements(driver, PageHomeBusquedaProducto.class);
		Thread.sleep(3000);
		producto.cerrarModalCodigoPostal();
		Thread.sleep(3000);
		Reporter.log("Ingresamos " + busqueda + " en la caja de busqueda");
		producto.ingresarArticuloparaBusqueda(busqueda);
		Thread.sleep(3000);
		Reporter.log("Seleccionamos " + tipoProducto + " de las opciones disponibles");
		producto.seleccionarTipoProducto(tipoProducto);
		Reporter.log("Se valida que al ingresar " + busqueda + " y seleccionar la categoria " + tipoProducto + " se visualice en el breadcrumb " + tipoProducto);
		Assert.assertEquals(tipoProducto, producto.validarBreadCrumbProductoSeleccionado(tipoProducto));
		
	}

	@AfterMethod
	public void cerrarBrowser() {
		Reporter.log("Cerrar browser");
		driver.close();

	}
}
