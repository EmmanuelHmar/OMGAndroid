package emmanuelhmar.omgandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener{
    TextView mainTextView;
    Button mainButton;
    EditText mainEditText;
    ListView mainListView;
    ArrayAdapter<String> mArrayAdapter;
    ArrayList<String> mNameList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //1. Access the TextView defined in layout XML
        //and then set its text
        mainTextView = (TextView) findViewById(R.id.main_textView);
        String text = "Set in Java!";
        mainTextView.setText(text);
        mainButton = (Button) findViewById(R.id.main_button);
        mainButton.setOnClickListener(this);
        mainEditText = (EditText) findViewById(R.id.main_editText);

//        4. Access the ListView
        mainListView = (ListView) findViewById(R.id.main_listView);

//        Create an ArrayAdapter for the ListView
        mArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mNameList);

//        Set the Listview to use the ArrayAdapter
        mainListView.setAdapter(mArrayAdapter);

        //5. Set this activity to react to list items being pressed
        mainListView.setOnItemClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate the menu; this adds items to the action bar if it present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        //Take what was typed in EdiText
        //Use it in TextView
        String text = mainEditText.getText().toString() + " is learning android";
        mainTextView.setText(text);

//        Also add that value to the list shown in ListView
        mNameList.add(mainEditText.getText().toString());
        mArrayAdapter.notifyDataSetChanged();
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//        Log the item's position and contents to the console in Debug
        Log.d("omg Android", position + ":" + mNameList.get(position));
    }
}
