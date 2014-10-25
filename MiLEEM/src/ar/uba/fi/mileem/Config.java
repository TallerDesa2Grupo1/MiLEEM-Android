package ar.uba.fi.mileem;

public class Config {
	
	/* MiLEEM SERVER*/
	public static  String SITEBASEURL = "http://"+MileemApp.getContext().getString(R.string.intent_url) + "/";
	public static  String BASEURL = SITEBASEURL + "api/";
	public static final String PUBLICACIONES_CONTROLLER = "publications/";
	public static final String PUBLICACION_CONTROLLER = "publication/";
	public static final String TIPO_PROPIEDADES_CONTROLLER = "property_types/";
	public static final String TIPO_OPERACIONES_CONTROLLER = "operation_types/";
	public static final String BARRIOS_CONTROLLER = "neighborhoods/";
	public static final int CAPITAL_FEDERAL_ID = 1;
	public static final String	PUBLICATION_ID = "PUBLICATION_ID";
	public static final String PUBLIC_VIEW_URL = SITEBASEURL + PUBLICACIONES_CONTROLLER +"public_view/";
	public static final String STATS_DATA = "STATS_DATA";
	
	
	
}
