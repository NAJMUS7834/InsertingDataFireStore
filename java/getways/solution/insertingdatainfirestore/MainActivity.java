package getways.solution.insertingdatainfirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    Button button;
    EditText EditText;
    CheckBox checkBox;
    Spinner spinner;
    private FirebaseFirestore db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        EditText = findViewById(R.id.EditText);
        checkBox = findViewById(R.id.checkBox);
        spinner = findViewById(R.id.spinner);


        populateSpinner();

        db = FirebaseFirestore.getInstance();


    }

    private void populateSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }


    public void SbtHandle(View view) {

        String username = EditText.getText().toString();
        String category = spinner.getSelectedItem().toString();
        String status = new String();
        if(checkBox.isChecked()){
            status =checkBox.getText().toString();
        }
        if(!checkBox.isChecked()){
            status ="Not Available";
        }


        //Toast.makeText(MainActivity.this," " +username+" "+category+" "+status,Toast.LENGTH_LONG).show();

        Map<String, String> userMap = new HashMap<>();
        userMap.put("name",username);
        userMap.put("category",category);
        userMap.put("status",status);

        db.collection("users").add(userMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(MainActivity.this,"Success",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this,"eRROR"+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        }
}


