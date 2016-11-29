package edu.udem.feriaint.Activities;



import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;

import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.udem.feriaint.Modelos.ContenidoCultural;
import edu.udem.feriaint.R;

public class Detalle_ContCultural  extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private String TAG;
    ContenidoCultural contCult;
    private View v;
    private LinearLayout linearLayout;
    private ScrollView sv;
    private TextView tituloContCult;
    private String videoId;
    private boolean video=false;
    private int thumbnailId;
    private ArrayList<String> videosId;
    private ArrayList<Integer> imageVideoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_detalle__cont_cultural);


        TAG = this.getClass().getSimpleName();

        videosId=new ArrayList<>();
        imageVideoId=new ArrayList<>();

        v = getLayoutInflater().inflate(R.layout.activity_detalle__cont_cultural, null);

        sv = (ScrollView) v.findViewById(R.id.scrollView);
        linearLayout = (LinearLayout) v.findViewById(R.id.layoutDetalle);

        tituloContCult = (TextView) v.findViewById(R.id.txtTituloContCult);
        //ImageView img=(ImageView) findViewById(R.id.imgContCultural);
        //getObject
        Bundle b = getIntent().getExtras();
        // contCult=b.getParcelable("contCult");

        contCult = new ContenidoCultural(b.getString("titulo"), b.getString("tema"));

        contCult.setFormato(b.getStringArrayList("formato"));
        contCult.setContenido(b.getStringArrayList("contenido"));

        tituloContCult.setText(contCult.getTitulo());
        Log.d(TAG, contCult.toString());

        try {
            setLayout();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public void setLayout() throws IOException {
        boolean imgPortada = false;
        // ArrayList<String> formato=contCult.getFormato();
        thumbnailId=0;

        for (int i = 0; i < contCult.getFormato().size(); i++) {
            String f = contCult.getFormato().get(i);
            String contenido = contCult.getContenido().get(i);
            Log.e(TAG, "add layout " + f + " " + contenido);
            switch (f) {


                case "lineaTexto":
                    TextView lt = new TextView(this);
                    lt.setText(contenido);
                    linearLayout.addView(lt);

                    break;

                case "areaTexto":
                    TextView at = new TextView(this);
                    at.setText(contenido);
                    linearLayout.addView(at);
                    break;

                case "imagen":

                    ImageView img = new ImageView(this);


                    // img.setText(f);
                    //Initializing image loader
                   /* URL url = new URL("http://image10.bizrate-images.com/resize?sq=60&uid=2216744464");
                    Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    img.setImageBitmap(bmp);*/

                    /*if (!imgPortada) {
                        ImageView portada = (ImageView) v.findViewById(R.id.imgContCulturalPortada);

                        Picasso.with(this).load(contenido).into(portada);

                        Log.d(TAG, "No tiene Img Portada");
                        imgPortada = true;

                    }*/

                    Glide.with(v.getContext())
                            .load(contenido)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(img);

                    linearLayout.addView(img);

                    break;
                case "video":


                video=false;
                    //si no es youtube
                   /* VideoView video = new VideoView(this);
                    video.setMediaController(new MediaController(this));
                    video.setVideoURI(Uri.parse(contenido));*/

                    final ImageView imagenVideo = new ImageView(this);
                    videoId = getVideoId(contenido);
                    videosId.add(videoId);



                    Glide.with(v.getContext())
                            .load("http://img.youtube.com/vi/" + videoId + "/0.jpg")
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(imagenVideo);



                    final YouTubePlayer.OnInitializedListener mOnInitListener=new YouTubePlayer.OnInitializedListener() {
                        @Override
                        public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean restored) {
                            if(!restored)
                            {
                                youTubePlayer.loadVideo(videoId);
                            }
                        }

                        @Override
                        public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                            Log.e("onclick", "Falla ONCLICK");
                        }
                    };

                    linearLayout.addView(imagenVideo);


                    imagenVideo.setId(thumbnailId);
                    imagenVideo.setOnClickListener(new View.OnClickListener()  {
                        @Override
                        public void onClick(View view) {

                            videoId= videosId.get(imagenVideo.getId());



                            if(thumbnailId>1)
                            {

                                Intent intent = new Intent(getApplicationContext(), PlayerActivity.class);
                                intent.putExtra("VIDEO_ID", videoId);
                                startActivity(intent);
                            }
                            /*Intent intent =YouTubeStandalonePlayer.createVideoIntent(, MainActivity.API_KEY, videoId);
                            startActivity(intent);*/
                            else
                            {
                                reproducirVideo(imagenVideo.getId());
                            }


                            Log.e("onClick", "IMAGEN TO VIDEO" + imagenVideo.getId());

                        }
                    });




                    thumbnailId++;

                    break;
                case "audio":
                  /*  MediaPlayer audio = new MediaPlayer();
                    audio.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    audio.setDataSource(contenido);
                    audio.prepare();
                    audio.start();*/
                    break;


                case "paginaWeb":
                    tituloContCult.setVisibility(View.GONE);
                    WebView paginaWeb = new WebView(this);
                    paginaWeb.loadUrl(contenido);

                    paginaWeb.getSettings().setJavaScriptEnabled(true);
                    paginaWeb.getSettings().setUserAgentString("Mozilla/5.0 (iPhone; U; CPU like Mac OS X; en) AppleWebKit/420+ (KHTML, like Gecko) Version/3.0 Mobile/1A543a Safari/419.3");

                    paginaWeb.setWebViewClient(new WebViewClient());
                    linearLayout.addView(paginaWeb);

                    break;

                case "trivia2":


                    break;
                case "trivia4":
                    break;


                default:
                    break;
            }


        }




    setContentView(v);

}


    public void reproducirVideo(int id)
    {

        if (!imageVideoId.isEmpty())
        {



           // linearLayout.removeViewAt(imageVideoId.get(1));
            Log.e(TAG,"VIDEO quitar ID"+imageVideoId.get(1));
            //Quitar Video
            linearLayout.removeViewAt(imageVideoId.get(1));

            ImageView imagenPasada=(ImageView) v.findViewById(imageVideoId.get(0));
            imagenPasada.setVisibility(View.VISIBLE);
            imagenPasada.setVerticalScrollbarPosition(imageVideoId.get(1));
            imageVideoId.remove(1);
            imageVideoId.remove(0);

        }

        //linearLayout.removeViewAt(imagenVideo.getVerticalScrollbarPosition());

        //invisibles imagenes

        ImageView imagenVideo=(ImageView) v.findViewById(id);

      // YouTubePlayerView videoYT= (YouTubePlayerView)v.findViewById(R.id.player_view);
        YouTubePlayerView videoYT=new YouTubePlayerView(this);
        videoYT.setVerticalScrollbarPosition(imagenVideo.getVerticalScrollbarPosition());
        imagenVideo.setVisibility(View.GONE);
       // videoYT.setVisibility(View.VISIBLE);
        videoYT.setId(v.generateViewId());
        Log.e(TAG,"VIDEO ID"+videoYT.getId());
        linearLayout.addView(videoYT);
        videoYT.initialize(MainActivity.API_KEY,this);

        imageVideoId.add(imagenVideo.getId());
        imageVideoId.add(videoYT.getVerticalScrollbarPosition());
    }


    public static String getVideoId(String youtubeUrl) {
        String vId = null;
        Pattern pattern = Pattern.compile(
                "^https?://.*(?:youtu.be/|v/|u/\\w/|embed/|watch?v=)([^#&?]*).*$",
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(youtubeUrl);
        if (matcher.matches()){
            vId = matcher.group(1);
        }
        return vId;
    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean restored) {

        if(!restored)
        {
            youTubePlayer.loadVideo(videoId);
        }

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }
}
