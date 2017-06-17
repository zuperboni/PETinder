package com.petinder.petinder.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.iid.FirebaseInstanceId;
import com.petinder.petinder.R;
import com.petinder.petinder.modelo.Usuario;
import com.petinder.petinder.task.BuscaDadosUsuarioTask;
import com.petinder.petinder.task.CadastraUsuarioTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CadastroUsuarioActivity extends AppCompatActivity {
    Toolbar toolbar;
    Button btnAvancar;
    CollapsingToolbarLayout collapsingToolbarLayout;

    //para a selecao da imagem de perfil
    private String localArquivoFoto;
    private final int IMG_CAM = 1;
    private final int IMG_SDCARD = 2;
    String imagemDecodificada = "";
    Bitmap imagemfotoReduzida;
    Bitmap imagemfoto;

    private EditText nome;
    private ImageView fotoPerfil;
    private String auxFoto;
    private static EditText dataNasc;
    private EditText email;
    private EditText senha;
    private EditText confirmaSenha;
    private FloatingActionButton fabFoto;
    private Button btAvancar;
    private Boolean editar;
    private Usuario usuario;
    private String regId;

    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
    DateFormat brDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    DateFormat sqlDateFormat = new SimpleDateFormat("yyyy-MM-dd");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        //ColapsingToolbar com imagem e nome
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Registra o FCM ID
        onRegistrar();

        //Verifica se está entrando no modo Edição!
        Intent intent = getIntent();
        editar = intent.getBooleanExtra("editar", false);

        nome = (EditText) findViewById(R.id.editTextNome);
        email = (EditText) findViewById(R.id.editTextEmail);
        fabFoto = (FloatingActionButton) findViewById(R.id.fabButton);
        dataNasc = (EditText) findViewById(R.id.editTextDataNasc);
        senha = (EditText) findViewById(R.id.editTextSenha);
        confirmaSenha = (EditText) findViewById(R.id.editTextConfirmaSenha);
        fotoPerfil = (ImageView) findViewById(R.id.fotoPerfil);
        btAvancar = (Button) findViewById(R.id.btAvancar);

        dataNasc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dataFragment = new DatePickerFragment();
                dataFragment.show(getSupportFragmentManager(), "dataNasc");
            }
        });
        fabFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EscolherImagem();
            }
        });
        getSupportActionBar().setTitle("");

        btAvancar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                btAvancarOnClick();
            }
        });
        if (editar) {

            BuscaDadosPerfil();
//            LaySenha = (LinearLayout) findViewById(R.id.LaySenha);
//            LaySenha.setVisibility(View.GONE);
            email.setVisibility(View.GONE);
        }
    }
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            DatePickerDialog picker = new DatePickerDialog(getActivity(), this, year, month, day);
            picker.getDatePicker().setSpinnersShown(true);
            picker.getDatePicker().setCalendarViewShown(false);
            return picker;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user

            String txtmes = "";
            String txtdia = "";

            month = month + 1;

            if (month < 10) {
                txtmes = "0" + String.valueOf(month);
            } else {
                txtmes = String.valueOf(month);
            }
            if (day < 10) {
                txtdia = "0" + String.valueOf(day);
            } else {
                txtdia = String.valueOf(day);
            }

            String aux = txtdia + "/" + txtmes + "/" + year;
            dataNasc.setText(aux);

        }
    }

    private void EscolherImagem() {

        final CharSequence[] options = {getText(R.string.tirarfoto), getText(R.string.escolherdagaleria), getText(R.string.cancelar)};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.escolhafoto);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals(getText(R.string.tirarfoto))) {
                    localArquivoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";
                    Intent irParaCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    irParaCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(localArquivoFoto)));
                    irParaCamera.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                    startActivityForResult(irParaCamera, IMG_CAM);
                } else if (options[item].equals(getText(R.string.escolherdagaleria))) {
                    Intent irParaGaleria = new Intent(Intent.ACTION_GET_CONTENT);
                    irParaGaleria.setType("image/*");
                    startActivityForResult(irParaGaleria, IMG_SDCARD);

                } else if (options[item].equals(getText(R.string.cancelar))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    //Recebe e trata informacoes das aplicacoes responsaveis pela foto
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == IMG_CAM && resultCode == RESULT_OK) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(localArquivoFoto, options);
            Log.i("tamanho original", String.valueOf(options.outHeight) + " " + String.valueOf(options.outWidth));
            if (options.outHeight >= 4000 || options.outWidth >= 4000) {
                options.inSampleSize = 4;
            } else if (options.outHeight >= 2000 || options.outWidth >= 2000) {
                options.inSampleSize = 2;
            }
            options.inJustDecodeBounds = false;
            imagemfoto = BitmapFactory.decodeFile(localArquivoFoto, options);
            Log.i("tamanho carregado", String.valueOf(imagemfoto.getHeight()) + " " + String.valueOf(imagemfoto.getWidth()));

            //Diminuir foto proporcionalmente para o view
            int scaleFactor = Math.min(imagemfoto.getWidth() / fotoPerfil.getWidth(),
                    imagemfoto.getHeight() / fotoPerfil.getHeight());
            if (scaleFactor <= 0)
                scaleFactor = 1;
            imagemfotoReduzida = Bitmap.createScaledBitmap(imagemfoto, imagemfoto.getWidth() / scaleFactor,
                    imagemfoto.getHeight() / scaleFactor, true);
            fotoPerfil.setImageBitmap(imagemfotoReduzida);
            fotoPerfil.setTag(localArquivoFoto);

        } else if (data != null && requestCode == IMG_SDCARD && resultCode == RESULT_OK) {
            Uri img = data.getData();

            InputStream inputStream;
            try {
                inputStream = this.getContentResolver().openInputStream(img);
                InputStream inputStream2 = this.getContentResolver().openInputStream(img);
                //Imagem original
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                imagemfoto = BitmapFactory.decodeStream(inputStream, null, options);
                Log.i("tamanho original", String.valueOf(options.outHeight) + " " + String.valueOf(options.outWidth));
                if (options.outHeight >= 4000 || options.outWidth >= 4000) {
                    options.inSampleSize = 4;
                } else if (options.outHeight >= 2000 || options.outWidth >= 2000) {
                    options.inSampleSize = 2;
                }
                options.inJustDecodeBounds = false;
                imagemfoto = BitmapFactory.decodeStream(inputStream2, null, options);
                Log.i("tamanho carregado", String.valueOf(imagemfoto.getHeight()) + " " + String.valueOf(imagemfoto.getWidth()));

                //Diminuir foto proporcionalmente para o view
                int scaleFactor = Math.min(imagemfoto.getWidth() / fotoPerfil.getWidth(),
                        imagemfoto.getHeight() / fotoPerfil.getHeight());
                if (scaleFactor <= 0)
                    scaleFactor = 1;
                imagemfotoReduzida = Bitmap.createScaledBitmap(imagemfoto, imagemfoto.getWidth() / scaleFactor,
                        imagemfoto.getHeight() / scaleFactor, true);
                fotoPerfil.setImageBitmap(imagemfotoReduzida);
                fotoPerfil.setScaleType(ImageView.ScaleType.CENTER_CROP);
                fotoPerfil.setTag(localArquivoFoto);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Erro ao carregar a imagem", Toast.LENGTH_LONG).show();
            }


        }

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public Boolean validaCampos() {

        Boolean retorno = true;

        if (nome.getText().toString().equals("")) {
            nome.setError(getResources().getString(R.string.preenchacampo));
            nome.setFocusable(true);
            retorno = false;

        }
        if (dataNasc.getText().toString().equals("")) {
            dataNasc.setError(getResources().getString(R.string.preenchacampo));
            dataNasc.setFocusable(true);
            retorno = false;

        }

        if (email.getText().toString().equals("")) {
            email.setError(this.getResources().getString(R.string.preenchacampo));
            email.setFocusable(true);
            retorno = false;
        } else {
            String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

            Pattern pattern = Pattern.compile(EMAIL_PATTERN);
            Matcher matcher = pattern.matcher(email.getText().toString());
            retorno = matcher.matches();
            if (!matcher.matches()) {
                email.setError(getResources().getString(R.string.emailInvalido));
            }

        }
        if (senha.getText().toString().equals("")) {
            senha.setError(getResources().getString(R.string.preenchacampo));
            senha.setFocusable(true);
            retorno = false;
        } else {
            if (senha.getText().toString().length() < 5) {
                senha.setError(getResources().getString(R.string.senhacurta));
                retorno = false;
            }
        }
        if (confirmaSenha.getText().toString().equals("")) {
            confirmaSenha.setError(getResources().getString(R.string.preenchacampo));
            confirmaSenha.setFocusable(true);
            retorno = false;
        } else {
            if (!confirmaSenha.getText().toString().equals(senha.getText().toString())) {
                confirmaSenha.setError(getResources().getString(R.string.senhasdiferentes));
                retorno = false;
            }
        }

        return retorno;
    }

    // Faz o registro no GCM
    public void onRegistrar() {
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.i("FIREBASE TOKEN: ", token);
    }


    public void onErroCadastro() {
        Toast.makeText(this, R.string.opserro, Toast.LENGTH_SHORT).show();
    }


    public void BuscaDadosPerfil() {
        usuario = new Usuario();
        SharedPreferences prefs = getSharedPreferences("Configuracoes", Context.MODE_PRIVATE);
        usuario.setEmail(prefs.getString("email", ""));
        BuscaDadosUsuarioTask taskDados = new BuscaDadosUsuarioTask(CadastroUsuarioActivity.this, usuario);
        taskDados.execute();

    }

    public void PreencheCampos(Usuario usuario) {

        nome.setText(usuario.getNome());
        dataNasc.setText(brDateFormat.format(usuario.getDtNasc()));
        email.setText(usuario.getEmail());
        senha.setText(usuario.getSenha());
        confirmaSenha.setText(usuario.getSenha());
        auxFoto = usuario.getImagemPerfil();

        if (!usuario.getImagemPerfil().equals("")) {
            Glide.with(this).load(getResources().getString(R.string.imageserver) + usuario.getImagemPerfil()).into(fotoPerfil);
        }
    }

    public void btAvancarOnClick() {
        if (validaCampos()) {
            //Tratar ImageViwew
            //localiza e transforma em um array de bytes
            if (imagemfoto != null) {
                ByteArrayOutputStream bAOS = new ByteArrayOutputStream();
                imagemfotoReduzida.compress(Bitmap.CompressFormat.JPEG, 20, bAOS);
                byte[] imagemArrayBytes = bAOS.toByteArray();
                //decodifica com a classe BASE64 e transforma em string
                imagemDecodificada = Base64.encodeToString(imagemArrayBytes, Base64.DEFAULT);
            }

            SharedPreferences prefs = getSharedPreferences("Configuracoes", Context.MODE_PRIVATE);

            if (!prefs.getString("gcmId", "").equals("")) {

                regId = prefs.getString("gcmId", "");
                //Salvar as informacoes aqui!!!
                usuario = new Usuario();
                usuario.addGcmId(regId);
                usuario.setNome(nome.getText().toString());
                usuario.setEmail(email.getText().toString().toLowerCase());
                try {
                    usuario.setDtNasc(brDateFormat.parse(dataNasc.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                usuario.setSenha(senha.getText().toString());

                if (!editar) {
                    CadastraUsuarioTask taskCadastro = new CadastraUsuarioTask(CadastroUsuarioActivity.this, usuario, imagemfoto);
                    taskCadastro.execute();
                } else {
                    /* Task editar - Criar!
                    usuario.setImagemPerfil(auxFoto);
                    EditaUsuarioTask task = new EditaUsuarioTask(CadastroUsuarioActivity.this, usuario, imagemfoto);
                    task.execute();
                    */
                }
            }
        }
    }

}

