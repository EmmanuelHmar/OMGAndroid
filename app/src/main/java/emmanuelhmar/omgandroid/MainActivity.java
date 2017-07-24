package emmanuelhmar.omgandroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener{
    TextView mainTextView;
    Button mainButton;
    EditText mainEditText;
    ListView mainListView;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> nameList = new ArrayList<>();
    ShareActionProvider shareActionProvider;
    private static final String PREFS = "prefs";
    private static final String PREF_NAME = "name";
    SharedPreferences sharedPreferences;



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
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nameList);

//        Set the Listview to use the ArrayAdapter
        mainListView.setAdapter(arrayAdapter);

        //5. Set this activity to react to list items being pressed
        mainListView.setOnItemClickListener(this);

        //7. Greet the user, or ask for their name if new
        displayWelcome();
    }

    private void displayWelcome() {
//        Access the device's key-value storage
        sharedPreferences = getSharedPreferences(PREFS, MODE_PRIVATE);

//        Read the user's name or an empty string if noting found
        String name = sharedPreferences.getString(PREF_NAME, "");

        if (name.length() > 0) {
//            if the name is valid, display a Toast welcoming them
            Toast.makeText(this, "Welcome back, " + name + "!", Toast.LENGTH_LONG).show();
        } else {
//            otherwise, show a dialog asking for their name
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Hello!");
            alert.setMessage("What is your name?");

//            Create EditText for entry
            final EditText input = new EditText(this);
            alert.setView(input);

//            Make an "OK" button to save the name
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int whichButton) {
//                    Grab the EditText's input
                    String inputName = input.getText().toString();

//                    Put it into memory (don't forget to commit!)
                    SharedPreferences.Editor e = sharedPreferences.edit();
                    e.putString(PREF_NAME, inputName);
                    e.apply();

                    //Welcome the new user
                    Toast.makeText(getApplicationContext(), "Welcome, " + inputName + "!",
                            Toast.LENGTH_LONG).show();
                }
            });

//            Make a Cancel button that dismisses the alert
            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });

            alert.show();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate the menu_main; this adds items to the action bar if it present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
//        Access the Share Item defined in menu XML
        MenuItem shareItem = menu.findItem(R.id.menu_item_share);

//        Access the object responsible for putting together the sharing submenu
        if (shareItem != null) {
            shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        }

//        Create an Intent to share your content
        setShareIntent();

        return true;
    }

    private void setShareIntent(){
        if (shareActionProvider != null) {
//            Create an Intent with the contents of the TextView
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Android Development");
            shareIntent.putExtra(Intent.EXTRA_TEXT, mainTextView.getText());

//            Make sure the provider knows it should work with that intent
            shareActionProvider.setShareIntent(shareIntent);
        }
    }


    @Override
    public void onClick(View view) {
        //Take what was typed in EdiText
        //Use it in TextView
        String text = mainEditText.getText().toString() + " is learning android";
        mainTextView.setText(text);

//        Also add that value to the list shown in ListView
        nameList.add(mainEditText.getText().toString());
        arrayAdapter.notifyDataSetChanged();

//        6. The text you'd like to share has changed and you need to update
        setShareIntent();
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//        Log the item's position and contents to the console in Debug
        Log.d("omg Android", position + ":" + nameList.get(position));
    }
}