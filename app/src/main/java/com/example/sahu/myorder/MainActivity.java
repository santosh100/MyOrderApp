package com.example.sahu.myorder;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import static com.example.sahu.myorder.R.string.price;
public class MainActivity extends AppCompatActivity {

    int quantity = 0;
    int initialPrice = 5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        CheckBox whipped = (CheckBox) findViewById(R.id.checkbox_1);
        CheckBox chocolate = (CheckBox) findViewById(R.id.checkbox_2);

        boolean whippedBool = whipped.isChecked();
        boolean chocolateBool = chocolate.isChecked();

        calculate(whippedBool, chocolateBool);

        EditText nameEditText = (EditText) findViewById(R.id.input_name_Edit_text);
        String name = nameEditText.getText().toString();

        String priceMessage = createOrderSummary(name,whippedBool, chocolateBool);

        String[] to = {"sahu18456@gmail.com"};
        Intent intent =  new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL,to);
        intent.putExtra(Intent.EXTRA_SUBJECT,"MyOrder For "+name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);

        if (intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
        }
    }

    public void increament(View view) {
        if (quantity < 10)
            quantity++;
        else
            Toast.makeText(this, "You cannot have more than 10 coffees", Toast.LENGTH_LONG).show();
        displayQuantity();
    }

    public void decreament(View view) {

        if (quantity > 0)
            quantity--;
        else
            Toast.makeText(this, "Order can never be less than Zero", Toast.LENGTH_SHORT).show();
        displayQuantity();
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity() {
        TextView priceTextView = (TextView) findViewById(R.id.quantity_text_view);
        priceTextView.setText("" + quantity);
    }

    public void calculate(boolean whippedBool, boolean chocolateBool) {
        initialPrice = 5;
        if (whippedBool)
            initialPrice++;
        if (chocolateBool)
            initialPrice += 2;

    }

    public String createOrderSummary(String name,boolean whippedBool, boolean chocolateBool) {
        String msg;

        msg = "\n"+getString(R.string.client_name,name)
                +"\n"+getString(R.string.create_summary_whipped,whippedBool)
                +"\n"+getString(R.string.create_summary_chocolate,chocolateBool)
                +"\n"+getString(R.string.total_order,quantity)
                +"\n"+getString(price, NumberFormat.getCurrencyInstance().format(initialPrice * quantity))
                +"\n"+getString(R.string.thanks);
        return msg;
    }

}
