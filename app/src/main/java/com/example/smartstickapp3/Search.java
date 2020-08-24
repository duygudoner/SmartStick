package com.example.smartstickapp3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class Search extends AppCompatActivity {

    RecyclerView plantsRecyclerView;
    PlantAdapter plantAdapter;
    List<PlantItem> mData;
    EditText search_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        plantsRecyclerView = findViewById(R.id.plant_rc);
        search_input = findViewById(R.id.edt_search);
        mData = new ArrayList<>();

        mData.add(new PlantItem("Atatürk Çiçeği","Türkiye’de yetiştirilmesi ve tanınmasına önayak olduğu için " +
                "adını Mustafa Kemal’den alan Atatürk çiçeği göz kamaştırıcı kırmızısıyla dikkat çeken bir salon bitkisidir. ",R.drawable.ataturk_cicegi));
        mData.add(new PlantItem("Barış Çiçeği","İri yeşil yaprakları ve beyaz açan çiçekleriyle evde tropik etki " +
                "yaratan oldukça hoş bir çiçektir. Uzun ömürlüdür ve bakımı kolaydır. Yazın daha fazla olmakla birlikte dört mevsim çiçek açar.",R.drawable.baris_cicegi));
        mData.add(new PlantItem("Begonya Çiçeği","Ev ortamında yetişmeye elverişli olan canlı ve renkli bir süs " +
                "bitkisidir. Çok çeşitli renklere sahip olan bu çiçek genellikle beyaz ve kırmızı renklerde bulunabilir.",R.drawable.begonya_cicegi));
        mData.add(new PlantItem("Flamingo Çiçeği","Kalp şeklindeki yaprakları ve canlı çiçekleriyle oldukça hoş bir" +
                " çiçektir. Uzun ömürlü bir çiçektir, bakımı kolaydır. Yazın daha fazla olmakla birlikte dört mevsim çiçek açar.\n",R.drawable.flamingo_cicegi));
        mData.add(new PlantItem("Guzmanya Çiçeği"," Uzun süre bozulmadan duran yaprakları nedeniyle süs bitkisi " +
                "olarak satılmaktadır. \n",R.drawable.guzmanya_cicegi));
        mData.add(new PlantItem("İpek Çiçeği(Kedi Tırnağı)","Çok güneşli ve susuz yerlere uyum sağladığı ve " +
                "rengarenk çiçekleri olduğu için bahçelerde çokça kullanılır.Semizotugillerdendir.60 Kadar türü vardır.",R.drawable.keditirnagi_cicegi));
        mData.add(new PlantItem("Kalanşo","Evde yetiştirilebilen harika bir çiçektir. Küçük ama küme halinde çiçek" +
                " açar. Pembe, beyaz, kırmızı bir çok  rengi mevcuttur.",R.drawable.kalanso_cicegi));
        mData.add(new PlantItem("Küpe Çiçeği","Büyük ağaç olan tek tür Fuchsia excorticata Yeni Zelanda’ya özgü bir" +
                " küpe çiçeği ağacıdır. 15 metreye kadar uzayabilir. Genellikle az güneşli, serin ve çok esintili şartlarda " +
                "yaşayabilir. Kışın yapraklarını dökerek dinlenirler.",R.drawable.kupe_cicegi));
        mData.add(new PlantItem("Menekşe Çiçeği","Otsu bir bitki olmasına rağmen çok yıllık da olduğundan iyi bakıldığında" +
                " ya da yetişmesi için uygun şartlar sağlandığında uzun yıllar hayatta kalabilir. Menekşe, uygun koşullarda doğada " +
                "kendiliğinden yetişebildiği gibi saksı bitkisi olarak evlerde, bahçe ve balkonlarda da kendine çok sık yer bulan süs bitkileri arasındadır.",R.drawable.menekse_cicegi));
        mData.add(new PlantItem("Sardunya Çiçeği","Yaprakları hoş kokulu, desenli, parlak renkli demet demet çiçeklere" +
                " sahiptir.Çok yıllık bir ilkbahar bitkisidir. Kırmızı, pembe, beyaz, sarı, mor ve turuncu renkli çiçekleri vardır.",R.drawable.sardunya_cicegi));
        mData.add(new PlantItem("Sümbül Çiçeği","Soğanlı bahar bitkilerinden olup birçok çeşidi vardır. Yetiştirilmesi" +
                " basittir. Aynı saksıda yıllarca yaşayabilir. Kış aylarında yeşermeye başlar. Yılda sadece bir defa ilkbahar " +
                "başında çiçek açar. Güzel kokulu olmasından ötürü her yerde sevilir.",R.drawable.sumbul_cicegi));
        mData.add(new PlantItem("Yasemin Çiçeği","Ev ve işyerlerine de farklı bir atmosfer katan yasemin çiçeği " +
                "sayesinde bulunduğunuz ortamı daha hoş ve dekoratif hale getirebilirsiniz. Yasemin çiçeği daha uzun ömürlü " +
                "olabileceği gibi daha kaliteli bir şekilde de çiçek açabilir.",R.drawable.yasemin_cicegi));
        mData.add(new PlantItem("Yılbaşı Çiçeği","Yıl içinde çok kez çiçek açar fakat en fazla çiçeklendiği zaman " +
                "kış mevsiminin başıdır. Direkt güneş ışığı almayan aydınlık bir ortamda olmalıdır. Çok sıcağı sevmez. " +
                "Yaz aylarında düzenli olarak sulanmalıdır.",R.drawable.yilbasi_cicegi));

        plantAdapter = new PlantAdapter(this,mData);
        plantsRecyclerView.setAdapter(plantAdapter);
        plantsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        search_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                plantAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}
