package dz.tdm.AmrineMadji;

import android.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.MetadataChangeSet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dz.tdm.AmrineMadji.adapters.CropOptionAdapter;
import dz.tdm.AmrineMadji.fragments.FirstFragment;
import dz.tdm.AmrineMadji.fragments.FragmentDrawer;
import dz.tdm.AmrineMadji.fragments.SecondFragment;
import dz.tdm.AmrineMadji.models.Localisation;
import dz.tdm.AmrineMadji.models.Sinistre;
import dz.tdm.AmrineMadji.models.SinistreType;
import dz.tdm.AmrineMadji.models.User;
import dz.tdm.AmrineMadji.utils.CropOption;

public class Home extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    public static final int REQUEST_IMAGE_CAPTURE = 1994;
    public static final int REQUEST_TAKE_PHOTO = 1992;
    private static final int REQUEST_CODE_RESOLUTION = 1993;
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPageNo;
    private LocationManager lManager;
    private Location location;
    private double longitude, latitude;
    public android.support.v7.app.AlertDialog dialogType;
    private android.support.v7.app.AlertDialog dialogNbpersonnes;
    private android.support.v7.app.AlertDialog dialogInfospersonnes;
    private android.support.v7.app.AlertDialog photoSinistre;
    private final SinistreType[] type = new SinistreType[1];
    private int Nbpersonnes = 0;
    private String mCurrentPhotoPath;
    public List<User> userList = new ArrayList<>();
    private View dialogView;
    private String currentDateandTime;
    private Localisation localisation;
    private Sinistre sinistre;
    private ArrayList<Uri> uris = new ArrayList<>();
    private GoogleApiClient mGoogleApiClient;
    private boolean fileOperation = false;
    private Boolean aBoolean = true;
    private String mail_ = "";


    private Uri mImageCaptureUri;
    ImageView banar1;
    private static final int PICK_FROM_CAMERA = 1;
    private static final int CROP_FROM_CAMERA = 2;
    private static final int PICK_FROM_FILE = 3;
    FirstFragment firstFragment;

    private static String TAG = MainActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;

    public LocationManager manager;
    TelephonyManager telMgr;
    public int simState;



    private final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };


    public void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Votre GPS est désactivé")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public void buildAlertMessageNoSim() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Carte SIM non reconnue")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
        lManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        telMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        // Setup the viewPager
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        if (viewPager != null)
            viewPager.setAdapter(pagerAdapter);




        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp((DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);

        String[] web = Home.this.getResources().getStringArray(R.array.nav_drawer_labels);


        Integer[] imageId = {
                R.drawable.ic_home_black_24dp,
                R.drawable.ic_call_black_24dp,
                R.drawable.ic_email_black_24dp,
                R.drawable.ic_message_black_24dp,
                R.drawable.ic_place_black_24dp,
                R.drawable.ic_file_upload_black_24dp,
                R.drawable.ic_exit_to_app_black_24dp
        };
        simState = telMgr.getSimState();


        CustomList adapter1 = new
                CustomList(Home.this, web, imageId);
        ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter1);
        final Sinistre sinistre = new Sinistre();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        drawerFragment.mDrawerLayout.closeDrawers();
                        break;

                    case 1:


//TelephonyManager.SIM_STATE_READY

                        if (simState == 1){
                            buildAlertMessageNoSim();
                        }else {
                            sinistre.setSender(Splash.user);
                            sinistre.setTypeDeclaration("Appel");
                            Intent sIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:0553140453"));
                            sIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            if (ActivityCompat.checkSelfPermission(Home.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {}
                            startActivity(sIntent);
                        }

                        break;

                    case 2:
                        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                            buildAlertMessageNoGps();
                        }else {
                            geoCode();
                            showAlertType(1, 1);
                            dialogType.show();
                        }
                        userList = new ArrayList<User>();
                        break;

                    case 3:
                        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                            buildAlertMessageNoGps();
                        }else {
                            if (1 == simState){
                                buildAlertMessageNoSim();
                            }else {
                                geoCode();
                                showAlertType(2, 0);
                                dialogType.show();
                            }
                        }
                        userList = new ArrayList<User>();
                        break;

                    case 4:
                        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                            buildAlertMessageNoGps();
                        }else {
                            Intent intent = new Intent(Home.this, Map.class);
                            startActivity(intent);
                        }
                        break;

                    case 5:
                        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                            buildAlertMessageNoGps();
                        }else {
                            geoCode();
                            showAlertType(1, 2);
                            dialogType.show();
                        }
                        userList = new ArrayList<User>();
                        break;

                    case 6:
                        finish();
                        break;


                }
                
            }
        });



        final String [] items			= new String [] {"Take from camera", "Select from gallery"};
        ArrayAdapter<String> adapter	= new ArrayAdapter<String> (this, android.R.layout.select_dialog_item,items);
        AlertDialog.Builder builder		= new AlertDialog.Builder(this);

        builder.setTitle("Select Image");
        builder.setAdapter( adapter, new DialogInterface.OnClickListener() {
            public void onClick( DialogInterface dialog, int item ) { //pick from camera
                if (item == 0) {
                    Intent intent 	 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                            "tmp_avatar_" + String.valueOf(System.currentTimeMillis()) + ".jpg"));

                    intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);

                    try {
                        intent.putExtra("return-data", true);

                        startActivityForResult(intent, PICK_FROM_CAMERA);
                    } catch (ActivityNotFoundException e) {
                        e.printStackTrace();
                    }
                } else { //pick from file
                    Intent intent = new Intent();

                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);

                    startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_FILE);
                }
            }
        } );

        final AlertDialog dialog = builder.create();
        banar1=(ImageView)findViewById(R.id.banar1);
        banar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                dialog.show();

            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;

        switch (requestCode) {
            case PICK_FROM_CAMERA:
                doCrop();

                break;

            case PICK_FROM_FILE:
                mImageCaptureUri = data.getData();

                doCrop();

                break;

            case CROP_FROM_CAMERA:
                Bundle extras = data.getExtras();

                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data");

                    banar1.setImageBitmap(photo);
                }

                File f = new File(mImageCaptureUri.getPath());

                if (f.exists()) f.delete();

                break;

            case FirstFragment.REQUEST_IMAGE_CAPTURE:
                firstFragment.onActivityResult(requestCode, resultCode, data);
                break;

            case FirstFragment.REQUEST_TAKE_PHOTO:
                firstFragment.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }


    private void doCrop() {
        final ArrayList<CropOption> cropOptions = new ArrayList<CropOption>();

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");

        List<ResolveInfo> list = getPackageManager().queryIntentActivities( intent, 0 );

        int size = list.size();

        if (size == 0) {
            Toast.makeText(this, "Can not find image crop app", Toast.LENGTH_SHORT).show();

            return;
        } else {
            intent.setData(mImageCaptureUri);

            intent.putExtra("outputX", 200);
            intent.putExtra("outputY", 200);
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("scale", true);
            intent.putExtra("return-data", true);

            if (size == 1) {
                Intent i 		= new Intent(intent);
                ResolveInfo res	= list.get(0);

                i.setComponent( new ComponentName(res.activityInfo.packageName, res.activityInfo.name));

                startActivityForResult(i, CROP_FROM_CAMERA);
            } else {
                for (ResolveInfo res : list) {
                    final CropOption co = new CropOption();

                    co.title 	= getPackageManager().getApplicationLabel(res.activityInfo.applicationInfo);
                    co.icon		= getPackageManager().getApplicationIcon(res.activityInfo.applicationInfo);
                    co.appIntent= new Intent(intent);

                    co.appIntent.setComponent( new ComponentName(res.activityInfo.packageName, res.activityInfo.name));

                    cropOptions.add(co);
                }

                CropOptionAdapter adapter = new CropOptionAdapter(getApplicationContext(), cropOptions);

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Choose Crop App");
                builder.setAdapter( adapter, new DialogInterface.OnClickListener() {
                    public void onClick( DialogInterface dialog, int item ) {
                        startActivityForResult( cropOptions.get(item).appIntent, CROP_FROM_CAMERA);
                    }
                });

                builder.setOnCancelListener( new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel( DialogInterface dialog ) {

                        if (mImageCaptureUri != null ) {
                            getContentResolver().delete(mImageCaptureUri, null, null );
                            mImageCaptureUri = null;
                        }
                    }
                } );

                AlertDialog alert = builder.create();

                alert.show();
            }
        }
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public final int PAGE_COUNT = 2;

        private final String[] mTabsTitle = {"Accueil", "A propos"};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }



        @Override
        public Fragment getItem(int pos) {
            switch (pos) {

                case 0:
                    firstFragment = FirstFragment.newInstance(1);
                    return firstFragment;

                case 1:
                    return SecondFragment.newInstance(2);
            }
            return null;
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabsTitle[position];
        }
    }

    public class CustomList extends ArrayAdapter<String> {

        private final Activity context;
        private final String[] web;
        private final Integer[] imageId;
        public CustomList(Activity context,
                          String[] web, Integer[] imageId) {
            super(context, R.layout.nav_drawer_row, web);
            this.context = context;
            this.web = web;
            this.imageId = imageId;

        }


        @Override
        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();

            View rowView= inflater.inflate(R.layout.nav_drawer_row, null, true);
            TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

            ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
            txtTitle.setText(web[position]);

            imageView.setImageResource(imageId[position]);
            return rowView;
        }
    }




    protected void sendSMS() {
        Log.i("Send SMS", "");
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);

        smsIntent.setData(Uri.parse("smsto:"));
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address"  , new String ("0553140453"));
        String body = "";
        body = "Déclaration d'un Sinistre [URGENT]"
                +"\nExpediteur : "+Splash.user.getNom()
                +"\nN° permis : "+Splash.user.getPermis().getNumPermis()
                +"\nCatégorie permis : "+Splash.user.getPermis().getCategorie()
                +"\nN° Carte grise : "+Splash.user.getCarteGris().get(0).getNumCarte()
                +"\nN° de sachet : "+Splash.user.getCarteGris().get(0).getNumSacher()
                +"\nN° d'immatriculation : "+Splash.user.getCarteGris().get(0).getMatriculeVoiture()
                +"\nDate et Heure : "+currentDateandTime
                +"\nType de Sinistre : "+type[0]
                +"\nLocalistaion : "
                +"\n\tAdresse : "+localisation.getAddressLines()
                +"\n\tCode Pays : "+localisation.getCountryCode()
                +"\n\tLongitude : "+localisation.getLongitude()
                +"\n\tLatitude : "+localisation.getLatitude();
        smsIntent.putExtra("sms_body"  , body);

        sinistre.setTypeDeclaration("SMS");
        sinistre.setSender(Splash.user);

        try {
            startActivity(smsIntent);

            Log.i("Finished sending SMS...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(Home.this,
                    "SMS faild, please try again later.", Toast.LENGTH_SHORT).show();
        }
    }

    public void geoCode(){


            if (ActivityCompat.checkSelfPermission(Home.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Home.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            lManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);


            location = lManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();

            Geocoder geoCoder = new Geocoder(Home.this);
            List<Address> matches = null;
            try {
                matches = geoCoder.getFromLocation(latitude,longitude, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Address bestMatch = (matches.isEmpty() ? null : matches.get(0));

            String s = new String();

            s ="";
            for (int i = 0 ; i <= bestMatch.getMaxAddressLineIndex() ; ++i)
                s = s.concat(bestMatch.getAddressLine(i)+" ");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            currentDateandTime = sdf.format(new Date());

            localisation = new Localisation(s,bestMatch.getCountryCode(),bestMatch.getLongitude(),bestMatch.getLatitude());

    }

    public void showAlertType(final int choix, final int choix1){
        final CharSequence[] items = {SinistreType.ENTRE_VEHICULE.name(),SinistreType.SEUL.name(),SinistreType.STATIONNEMENT.name()};


        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(Home.this);
        builder.setTitle("Type de Sinistre :");
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {


                switch(item)
                {
                    case 0:
                        // Your code when first option seletced
                        type[0] = SinistreType.ENTRE_VEHICULE;
                        break;
                    case 1:
                        // Your code when 2nd  option seletced
                        type[0] = SinistreType.SEUL;
                        break;
                    case 2:
                        type[0] = SinistreType.STATIONNEMENT;
                        // Your code when 3rd option seletced
                        break;

                }
                dialogType.dismiss();
                if (choix == 1){

                    showAlertNbPersonne(choix1);
                    dialogNbpersonnes.show();
                }else {
                    sendSMS();
                }
            }
        });
        dialogType = builder.create();
    }

    private void showAlertNbPersonne(final int choix1){
        android.support.v7.app.AlertDialog.Builder dialogBuilder = new android.support.v7.app.AlertDialog.Builder(Home.this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.alert_dialog_nb_personne, null);
        dialogBuilder.setView(dialogView);


        final EditText nbPersone;

        nbPersone = (EditText) dialogView.findViewById(R.id.nbPersonnes);

        String positiveText = getString(android.R.string.ok);
        dialogBuilder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // positive button logic
                        if (nbPersone.getText().toString().length()>0){
                            Nbpersonnes = Integer.parseInt(nbPersone.getText().toString().trim());
                            for (int i = 0 ; i < Nbpersonnes ; i++){
                                showAlertDetailPersonnes(choix1);
                                dialogInfospersonnes.show();
                            }
                        }else
                            Toast.makeText(Home.this,"Operation annuler",Toast.LENGTH_LONG).show();

                    }
                });


        dialogNbpersonnes = dialogBuilder.create();
        // display dialog
    }

    private void showAlertDetailPersonnes( final int choix1){
        android.support.v7.app.AlertDialog.Builder dialogBuilder = new android.support.v7.app.AlertDialog.Builder(Home.this);
        final LayoutInflater inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.alert_dialog_for_picture, null);
        dialogBuilder.setView(dialogView);


        final EditText infoPersonne;
        final Button picJoin;

        infoPersonne = (EditText) dialogView.findViewById(R.id.infoPersonne);
        picJoin = (Button) dialogView.findViewById(R.id.picJoin);

        picJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        String positiveText = getString(android.R.string.ok);
        dialogBuilder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        User user = new User(mCurrentPhotoPath,infoPersonne.getText().toString().trim());
                        if (mCurrentPhotoPath != null){
                            userList.add(user);
                            java.io.File fileIn = new java.io.File(mCurrentPhotoPath);
                            Uri u = Uri.fromFile(fileIn);
                            uris.add(u);
                            Toast.makeText(Home.this,"Photo ajoutée",Toast.LENGTH_LONG).show();
                            mCurrentPhotoPath = null;
                        }else {
                            user.setPhotoPath(null);
                            userList.add(user);
                        }


                        if (userList.size() == Nbpersonnes) {
                            sinistre = new Sinistre(currentDateandTime, type[0], userList, localisation);
                            sinistre.setSender(Splash.user);
                            sinistre.setTypeDeclaration("Mail");

                            Splash.sinistres.add(sinistre);

                            String infoPersonne = "";
                            for (int i = 0; i < userList.size() ; i++)
                                infoPersonne = infoPersonne.concat(userList.get(i).getDesc()+"\n\t");

                            userList.clear();

                            mail_ = "\nExpediteur : "+Splash.user.getNom()
                                    +"\nN° permis : "+Splash.user.getPermis().getNumPermis()
                                    +"\nCatégorie permis : "+Splash.user.getPermis().getCategorie()
                                    +"\nN° Carte grise : "+Splash.user.getCarteGris().get(0).getNumCarte()
                                    +"\nN° de sachet : "+Splash.user.getCarteGris().get(0).getNumSacher()
                                    +"\nN° d'immatriculation : "+Splash.user.getCarteGris().get(0).getMatriculeVoiture()
                                    +"\nDate et Heure : "+sinistre.getDate()
                                    +"\nType de Sinistre : "+sinistre.getType()
                                    +"\nLocalistaion : "
                                    +"\n\tAdresse : "+sinistre.getLocalisation().getAddressLines()
                                    +"\n\tCode Pays : "+sinistre.getLocalisation().getCountryCode()
                                    +"\n\tLongitude : "+sinistre.getLocalisation().getLongitude()
                                    +"\n\tLatitude : "+sinistre.getLocalisation().getLatitude()
                                    +"\nIdentités des personnes :"
                                    +"\n\t"+infoPersonne;

                            if (choix1 == 1){
                                Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
                                intent.setType("text/plain");
                                intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "dm_amrine@esi.dz" });
                                intent.putExtra(Intent.EXTRA_SUBJECT, "Déclaration d'un Sinistre [URGENT]");
                                intent.putExtra(Intent.EXTRA_TEXT, mail_);
                                intent.putExtra(Intent.EXTRA_STREAM, uris);

                                startActivity(Intent.createChooser(intent, ""));
                            }else {
                                if (mGoogleApiClient == null) {

                                    /**
                                     * Create the API client and bind it to an instance variable.
                                     * We use this instance as the callback for connection and connection failures.
                                     * Since no account name is passed, the user is prompted to choose.
                                     */
                                    mGoogleApiClient = new GoogleApiClient.Builder(Home.this)
                                            .addApi(Drive.API)
                                            .addScope(Drive.SCOPE_FILE)
                                            .addConnectionCallbacks(Home.this)
                                            .addOnConnectionFailedListener(Home.this)
                                            .build();
                                }
                                mGoogleApiClient.connect();
                                onClickCreateFile();
                            }
                        }

                    }
                });


        dialogInfospersonnes = dialogBuilder.create();
        // display dialog
    }

    public void CreateFileOnGoogleDrive(final DriveApi.DriveContentsResult result){

        final DriveContents driveContents = result.getDriveContents();


        final DriveId driveId = DriveId.decodeFromString("DriveId:CAESABiu1gIgspq73NJVKAE=");

        // Perform I/O off the UI thread.
        new Thread() {
            @Override
            public void run() {
                // write content to DriveContents
                OutputStream outputStream = driveContents.getOutputStream();
                Writer writer = new OutputStreamWriter(outputStream);
                try {
                    writer.write(mail_);
                    writer.close();

                } catch (IOException e) {
                    Log.e(TAG, e.getMessage());
                }



                MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                        .setTitle("Sinistre"+sinistre.getDate())
                        .setMimeType("text/plain")
                        .setStarred(true).build();

                sinistre.setTypeDeclaration("Drive");
                sinistre.setSender(Splash.user);
                Splash.sinistres.add(sinistre);

                Drive.DriveApi.getFolder(mGoogleApiClient,driveId)
                        .createFile(mGoogleApiClient,changeSet,driveContents)
                        .setResultCallback(fileCallback);


                OutputStream outputStreamImg;
                DriveContents driveContentsImg;

                for (int j = 0 ; j < sinistre.getUserList().size(); j++){
                    outputStreamImg  = driveContents.getOutputStream();

                    try {
                        java.io.File fileIn = new java.io.File(sinistre.getUserList().get(j).getPhotoPath());
                        Uri u = Uri.fromFile(fileIn);
                        InputStream inputStream = getContentResolver().openInputStream(u);
                        if (inputStream != null) {
                            byte[] data = new byte[1024];
                            while (inputStream.read(data) != -1) {
                                //Reading data from local storage and writing to google drive
                                outputStreamImg.write(data);
                            }
                            inputStream.close();
                        }

                        outputStreamImg.close();
                    } catch (IOException e) {
                        Log.e(TAG, e.getMessage());
                    }


                    MetadataChangeSet changeSetImg = new MetadataChangeSet.Builder()
                            .setTitle("photo "+j+" "+sinistre.getDate())
                            .setMimeType("image/jpg")
                            .setStarred(true).build();

                    Drive.DriveApi.getFolder(mGoogleApiClient,driveId)
                            .createFile(mGoogleApiClient,changeSetImg,driveContents)
                            .setResultCallback(fileCallbackImg);

                }


            }


        }.start();
    }


    final private ResultCallback<DriveFolder.DriveFileResult> fileCallback = new
            ResultCallback<DriveFolder.DriveFileResult>() {
                @Override
                public void onResult(DriveFolder.DriveFileResult result) {
                    if (result.getStatus().isSuccess()) {

                        Toast.makeText(Home.this, "file created: "+""+
                                result.getDriveFile().getDriveId(), Toast.LENGTH_LONG).show();

                    }

                    return;

                }
            };

    final private ResultCallback<DriveFolder.DriveFileResult> fileCallbackImg = new
            ResultCallback<DriveFolder.DriveFileResult>() {
                @Override
                public void onResult(DriveFolder.DriveFileResult result) {
                    if (result.getStatus().isSuccess()) {

                        Toast.makeText(Home.this, "file created: "+""+
                                result.getDriveFile().getDriveId(), Toast.LENGTH_LONG).show();

                    }

                    return;

                }
            };


    final ResultCallback<DriveApi.DriveContentsResult> driveContentsCallback =
            new ResultCallback<DriveApi.DriveContentsResult>() {
                @Override
                public void onResult(DriveApi.DriveContentsResult result) {

                    if (result.getStatus().isSuccess()) {
                        if (fileOperation == true){

                            CreateFileOnGoogleDrive(result);

                        }
                    }
                }
            };

    public void onClickCreateFile(){

        fileOperation = true;
        // create new contents resource
        Drive.DriveApi.newDriveContents(mGoogleApiClient)
                .setResultCallback(driveContentsCallback);

    }



    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            java.io.File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(Home.this,
                        "com.example.android.fileprovider",
                        photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private java.io.File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        java.io.File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        java.io.File image = java.io.File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }



    @Override
    public void onResume() {
        super.onResume();



    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient != null) {

            // disconnect Google Android Drive API connection.
            mGoogleApiClient.disconnect();
        }
        super.onPause();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "GoogleApiClient connection suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        // Called whenever the API client fails to connect.
        Log.i(TAG, "GoogleApiClient connection failed: " + connectionResult.toString());

        if (!connectionResult.hasResolution()) {

            // show the localized error dialog.
            GoogleApiAvailability.getInstance().getErrorDialog(Home.this, connectionResult.getErrorCode(), 0).show();
            return;
        }

        /**
         *  The failure has a resolution. Resolve it.
         *  Called typically when the app is not yet authorized, and an  authorization
         *  dialog is displayed to the user.
         */

        try {

            connectionResult.startResolutionForResult(Home.this, REQUEST_CODE_RESOLUTION);

        } catch (IntentSender.SendIntentException e) {

            Log.e(TAG, "Exception while starting resolution activity", e);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent intent = new Intent(Home.this,Settings.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

}
