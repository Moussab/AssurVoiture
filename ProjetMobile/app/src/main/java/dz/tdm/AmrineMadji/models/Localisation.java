package dz.tdm.AmrineMadji.models;

/**
 * Created by Amine on 01/04/2017.
 */

public class Localisation {

    private String addressLines;
    private String countryCode;
    private Double longitude;
    private Double latitude;

    public Localisation(String addressLines, String countryCode, Double longitude, Double latitude) {
        this.addressLines = addressLines;
        this.countryCode = countryCode;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getAddressLines() {
        return addressLines;
    }

    public void setAddressLines(String addressLines) {
        this.addressLines = addressLines;
    }
}
