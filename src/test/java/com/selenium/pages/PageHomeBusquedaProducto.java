package com.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Reporter;

public class PageHomeBusquedaProducto {

	WebDriver driver;

	public PageHomeBusquedaProducto(WebDriver driver) {

		this.driver = driver;

	}

	@FindBy(xpath = "//button[@data-test-id='close-modal-button']")
	private WebElement cierreModalCodigoPostal;

	@FindBy(xpath = "//input[@placeholder='Buscar productos']")
	private WebElement cajaDeBusqueda;

	@FindBy(xpath = "//span[@class='categorySlug__TotalResult-shopping-ui__sc-1l2p1q1-7 kPRRxp']/span")
	private WebElement cantidadDeResultados;

	@FindBy(xpath = "//span[contains(text(),'Siguiente >')]")
	private WebElement botonSiguiente;
	
	@FindBy(xpath = "//body/div[@id='__next']/div[2]/div[3]/div[2]/div[1]/ol[1]/li[5]/a[1]/span[1]")
	private WebElement breadCrumb;

	int contadorProductos = 0;
	int cantidad = 0;
	int contador = 0;

	public void cerrarModalCodigoPostal() {

		cierreModalCodigoPostal.click();
	}

	public void ingresarArticuloparaBusqueda(String producto) {

		cajaDeBusqueda.sendKeys(producto);
		cajaDeBusqueda.sendKeys(Keys.ENTER);
	}

	public int cantidadXMarca() {

		cantidad = Integer.parseInt(cantidadDeResultados.getText());
		return cantidad;

	}

	public void seleccionarTipoProducto(String seleccion) {
		java.util.List<WebElement> allElements = driver
				.findElements(By.xpath("//ul/li/h4/a[@class='CategoriesFilter__A-shopping-ui__sc-ind2zf-0 cZJNKd']"));
		for (WebElement element : allElements) {
			if (element.getText().contains(seleccion)) {
				System.out.println(element.getText());
				element.click();
				break;
			}
		}
	}

	public void seleccionarMarca(String opcion) {
		java.util.List<WebElement> allElements = driver
				.findElements(By.xpath("//ul[@class='AggregationOptionsList-shopping-ui__sc-u332fq-0 gXzOkZ']/li"));
		for (WebElement element : allElements) {
			if (element.getText().contains(opcion)) {
				System.out.println(element.getText());
				element.click();
				break;
			}
		}
	}

	public boolean validarMarcaSeleccionada(String marca) {

		boolean bandera = true;

		while (contadorProductos < cantidad) {
			java.util.List<WebElement> products = driver
					.findElements(By.xpath("//ul[@data-test-id='results-list']/li/article/a/div/div/span"));
			for (WebElement product : products) {
				if (product.getText().contains(marca)) {
					Reporter.log("Producto: " + product.getText() + " contiene la marca " + marca);
					System.out.print("Producto: " + product.getText() + "\n");
				} else {
					Reporter.log("Producto con marca distinta: " + product.getText());
					bandera = false;
					break;
				}
				contadorProductos++;
				contador++;
				if (contadorProductos == 15 && cantidad > 15) {
					botonSiguiente.click();
					contador = 0;
				}
			}
		}
		return bandera;
	}

	public int validarCantidadDeLaMarcaSeleccionada(String marca) {

		while (contadorProductos < cantidad) {
			java.util.List<WebElement> products = driver
					.findElements(By.xpath("//ul[@data-test-id='results-list']/li/article/a/div/div/span"));
			for (WebElement product : products) {
				if (product.getText().contains(marca)) {
					System.out.print("Producto: " + product.getText() + "\n");
				} else {
					break;
				}
				contadorProductos++;
				contador++;
				if (contador == 15 && cantidad > 15) {
					botonSiguiente.click();
					contador = 0;
				}
			}
		}
		return contadorProductos;
	}
	
	public String validarBreadCrumbProductoSeleccionado(String producto) {
		
		String productoSeleccionado = breadCrumb.getText();
		System.out.print("BreadCrumb: " + productoSeleccionado + "\n");
		return productoSeleccionado;		
	}
	
}
