package com.example.rodrigoantunes.abasteceaqui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.rodrigoantunes.abasteceaqui.localizacao.Geolocalizacao;
import com.example.rodrigoantunes.abasteceaqui.model.ContainerPostos;
import com.example.rodrigoantunes.abasteceaqui.model.Posto;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback locationCallback;
    private Location lastLocation;
    private LatLng lastPosicao;
    private final int MY_REQUEST_CODE = 10;
    private Intent it;
    private ContainerPostos containerPostos;
    private Bitmap bmpPessoa, bmpBR, bmpIpi, bmpShell;
    private Geolocalizacao posicao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        carregaIcones();

        it = getIntent();

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        posicao = new Geolocalizacao(this);


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //Se o usuário retirou a permissão pro ACCESS_FINE_LOCATION
            //nós requisitamos novamente;
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_REQUEST_CODE);
        } else {
            //se a permissão para o ACCESS_FINE_LOCATION ainda existe
            //chama o método que pega a última localização conhecida

            posicao.getLastLocation();
            lastLocation= posicao.ultLocation;

            marcaPostosMapa();

            moveCameraToNewPosition();
         }

    }


    public void moveCameraToNewPosition() {
        if (lastLocation != null) {
            //LatLng: classe que encapsula apenas latitude e longitude

            //atualização da câmera. A CameraUpdateFactory fabrica
            //instância desta classe, com seus métodos estáticos
            //temos várias opções de atualização da câmera

            LatLng latLng = new LatLng(
                    lastLocation.getLatitude(),
                    lastLocation.getLongitude()
            );


            MarkerOptions nMkt = new MarkerOptions();
            nMkt.position(latLng);
            nMkt.title("Sua localização");

            nMkt.icon(BitmapDescriptorFactory.fromBitmap(bmpPessoa));


            mMap.addMarker(nMkt);


            //Teste
            //mMap.addMarker(new MarkerOptions().position(new LatLng(-30.156492, -51.185157)).title("POSTO CARLINHOS"));

            CameraUpdate cameraUpdate =
                    CameraUpdateFactory.newLatLngZoom(latLng, 13);
            mMap.animateCamera(cameraUpdate);

        }


    }


    public void marcaPostosMapa() {

        MarkerOptions nMkt;
        LatLng latLng;

        containerPostos = (ContainerPostos) it.getSerializableExtra("postos");

        for (Posto posto : containerPostos.postos) {
            Log.e("POSTO no mapa", posto.latitude.toString());

            latLng = new LatLng(
                    posto.latitude,
                    posto.longitude
            );


            nMkt = new MarkerOptions();
            nMkt.position(latLng);
            nMkt.title(posto.nome);

            mMap.addMarker(nMkt);

        }
    }

    public void carregaIcones() {

        bmpPessoa =
                BitmapFactory.decodeResource(
                        getResources(),
                        R.mipmap.pessoa_map
                );


        bmpBR =
                BitmapFactory.decodeResource(
                        getResources(),
                        R.mipmap.logo_br
                );


        bmpIpi =
                BitmapFactory.decodeResource(
                        getResources(),
                        R.mipmap.logo_ipi
                );


        bmpShell =
                BitmapFactory.decodeResource(
                        getResources(),
                        R.mipmap.logo_shell
                );

    }
}
