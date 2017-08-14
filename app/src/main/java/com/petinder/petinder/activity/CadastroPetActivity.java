package com.petinder.petinder.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.petinder.petinder.R;
import com.petinder.petinder.modelo.Pet;
import com.petinder.petinder.modelo.Raca;
import com.petinder.petinder.task.BuscaPerfilPetTask;
import com.petinder.petinder.task.CadastraPetTask;
import com.petinder.petinder.task.CadastraUsuarioTask;
import com.petinder.petinder.task.EditaPetTask;
import com.petinder.petinder.task.ListaRacasTask;
import com.petinder.petinder.util.Constantes;
import com.petinder.petinder.web.PetGson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carla on 21/06/2017.
 */

public class CadastroPetActivity extends AppCompatActivity {

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

    private EditText nomeDog;
    private ImageView fotoPerfilDog;
    private String auxFoto;
    private EditText idade;

    private Spinner raca;
    List<Raca> racas;
    private Raca racaAux;

    private EditText sobre;

    private RadioGroup sexo;
    private RadioButton rbMacho;
    private RadioButton rbFemea;

    private FloatingActionButton fabFoto;
    private Button btAvancar;
    private Boolean editar;
    private Pet pet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pet);

        //ColapsingToolbar com imagem e nome
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Verifica se está entrando no modo Edição!
        Intent intent = getIntent();
        editar = intent.getBooleanExtra("editar", false);

        inicializarVariaveis();
        inicializarAcoes();

        if (editar) {
            BuscaDadosPerfil();
        }
    }

    private void inicializarVariaveis() {
        nomeDog = (EditText) findViewById(R.id.editTextNomeDog);
        fabFoto = (FloatingActionButton) findViewById(R.id.cadpet_fabButton);
        idade = (EditText) findViewById(R.id.editTextIdadeDog);
        raca = (Spinner) findViewById(R.id.spinner_raca);

        sexo = (RadioGroup) findViewById(R.id.rgSexoDog);
        rbMacho = (RadioButton) findViewById(R.id.rbMacho);
        rbFemea = (RadioButton) findViewById(R.id.rbFemea);

        sobre = (EditText) findViewById(R.id.editTextSobreDog);
        fotoPerfilDog = (ImageView) findViewById(R.id.fotoPerfilDog);
        btAvancar = (Button) findViewById(R.id.cadpet_btn_avancar);

        ListaRacas();
    }

    private void inicializarAcoes() {
        fabFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EscolherImagem();
            }
        });

        btAvancar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
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

                    String sexoFM = "";

                    //Tratar RadioButton
                    switch (sexo.getCheckedRadioButtonId()) {
                        case R.id.rbMacho:
                            sexoFM = "Macho";
                            break;
                        case R.id.rbFemea:
                            sexoFM = "Femea";
                            break;
                    }

                    ArrayList<Pet> dados = new ArrayList<>();

                    Pet petAux = new Pet();
                    if (!editar) {
                        petAux.setCodPet(0);
                    } else {
                        petAux.setCodPet(pet.getCodPet());
                    }
                    petAux.setNome(nomeDog.getText().toString());
                    petAux.setIdade(Integer.parseInt(idade.getText().toString()));
                    petAux.setSexo(sexoFM);
                    petAux.setSobre(sobre.getText().toString());
                    petAux.setRaca(racaAux.getCodRaca());
                    petAux.setProprietario(Constantes.EMAIL_PROPRIETARIO);

                    if (!editar) {
                        CadastraPetTask taskCadastro = new CadastraPetTask(CadastroPetActivity.this, petAux, imagemfoto);
                        taskCadastro.execute();
                    } else {
                        EditaPetTask taskEditar = new EditaPetTask(CadastroPetActivity.this, petAux, imagemfoto);
                        taskEditar.execute();
                    }
                }
            }
        });

        raca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                racaAux = racas.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void ListaRacas(){
        ListaRacasTask task = new ListaRacasTask(this);
        task.execute();
    }

    public void AtualizaListaPets(List<Raca>racas){
        this.racas = racas;

        ArrayAdapter<Raca> spinnerRacaAdapter = new ArrayAdapter<Raca>(this, android.R.layout.simple_list_item_1, android.R.id.text1, racas);
        raca.setAdapter(spinnerRacaAdapter);
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
            int scaleFactor = Math.min(imagemfoto.getWidth() / fotoPerfilDog.getWidth(),
                    imagemfoto.getHeight() / fotoPerfilDog.getHeight());
            if (scaleFactor <= 0)
                scaleFactor = 1;
            imagemfotoReduzida = Bitmap.createScaledBitmap(imagemfoto, imagemfoto.getWidth() / scaleFactor,
                    imagemfoto.getHeight() / scaleFactor, true);
            fotoPerfilDog.setImageBitmap(imagemfotoReduzida);
            fotoPerfilDog.setTag(localArquivoFoto);

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
                int scaleFactor = Math.min(imagemfoto.getWidth() / fotoPerfilDog.getWidth(),
                        imagemfoto.getHeight() / fotoPerfilDog.getHeight());
                if (scaleFactor <= 0)
                    scaleFactor = 1;
                imagemfotoReduzida = Bitmap.createScaledBitmap(imagemfoto, imagemfoto.getWidth() / scaleFactor,
                        imagemfoto.getHeight() / scaleFactor, true);
                fotoPerfilDog.setImageBitmap(imagemfotoReduzida);
                fotoPerfilDog.setScaleType(ImageView.ScaleType.CENTER_CROP);
                fotoPerfilDog.setTag(localArquivoFoto);

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

        if (nomeDog.getText().toString().equals("")) {
            nomeDog.setError(getResources().getString(R.string.preenchacampo));
            nomeDog.setFocusable(true);
            retorno = false;

        }
        if (idade.getText().toString().equals("")) {
            idade.setError(getResources().getString(R.string.preenchacampo));
            idade.setFocusable(true);
            retorno = false;

        }

        return retorno;
    }

    public void onErroCadastro() {
        Toast.makeText(this, R.string.opserro, Toast.LENGTH_SHORT).show();
    }

    public void BuscaDadosPerfil() {
        pet = new Pet();
        pet.setCodPet(Constantes.PET_SELECIONADO);
        CadastraPetTask taskDados = new CadastraPetTask(CadastroPetActivity.this, pet, true);
        taskDados.execute();
    }

    public void PreencheCampos(Pet pet) {

        nomeDog.setText(pet.getNome());
        idade.setText(String.valueOf(pet.getIdade()));
        sobre.setText(pet.getSobre());
        if (pet.getSexo().equals("Femea")) {
            rbFemea.setChecked(true);
        } else {
            rbMacho.setChecked(true);
        }
        auxFoto = pet.getFotoPerfil();

        raca.setSelection(encontrePosition(raca, pet.getRaca()));

        if (!pet.getFotoPerfil().equals("")) {
            Glide.with(this).load(getResources().getString(R.string.imageserver) + pet.getFotoPerfil()).into(fotoPerfilDog);
        }

        this.pet = pet;
    }

    private int encontrePosition(Spinner spinnerRaca, int id) {
        int indice = 0;

        for (int i = 0; i < spinnerRaca.getCount(); i++) {
            Raca raca = (Raca) spinnerRaca.getItemAtPosition(i);
            if (raca.getCodRaca() == id) {
                indice = i;

                break;
            }
        }

        return indice;
    }
}