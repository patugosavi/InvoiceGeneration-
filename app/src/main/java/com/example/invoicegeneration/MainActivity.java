package com.example.invoicegeneration;

import static com.example.invoicegeneration.LogUtils.LOGE;
import static com.example.invoicegeneration.permission.PermissionsActivity.PERMISSION_REQUEST_CODE;
import static com.example.invoicegeneration.permission.PermissionsChecker.REQUIRED_PERMISSION;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.invoicegeneration.permission.PermissionsActivity;
import com.example.invoicegeneration.permission.PermissionsChecker;
import com.itextpdf.io.IOException;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfDocumentInfo;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
import com.itextpdf.kernel.pdf.canvas.draw.DottedLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Link;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Context mContext;

    PermissionsChecker checker;

    String dest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        checker = new PermissionsChecker(this);


        //Create time stamp
        Date date = new Date() ;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(date);

        dest = FileUtils.getAppPath(mContext) + timeStamp + ".pdf";
    }

    public void createPdf(String dest) {

        if (new File(dest).exists()) {
            new File(dest).delete();
        }

        try {
            /**
             * Creating Document
             */
            PdfWriter pdfWriter = new PdfWriter(new FileOutputStream(dest));
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            PdfDocumentInfo info = pdfDocument.getDocumentInfo();

            info.setTitle("Example of iText7 by Prathamesh Gosavi");
            info.setAuthor("Prathamesh Gosavi");
            info.setSubject("iText7 PDF Demo");
            info.setKeywords("iText, PDF, Prathamesh Gosavi");
            info.setCreator("A simple tutorial example");

//            Document document = new Document(pdfDocument, PageSize.A4, true);
            Document document = new Document(pdfDocument);

            /***
             * Variables for further use....
             */
            Color mColorAccent = new DeviceRgb(153, 204, 255);
            Color mColorBlack = new DeviceRgb(0, 0, 0);
            float mHeadingFontSize = 20.0f;
            float mValueFontSize = 26.0f;

            //my code
            DeviceRgb invoiceGreen=new DeviceRgb(51,204,51);
            DeviceRgb invoiceGray=new DeviceRgb(220,220,220);
            DeviceRgb invoicewhite=new DeviceRgb(255,255,255);

            /**
             * How to USE FONT....
             */
            PdfFont font = PdfFontFactory.createFont("assets/fonts/brandon_medium.otf", "UTF-8", true);



            // LINE SEPARATOR
            LineSeparator lineSeparator = new LineSeparator(new DottedLine());
            lineSeparator.setStrokeColor(new DeviceRgb(0, 0, 68));

            //             Title Order Details...
            // Adding Title....
            Text mOrderDetailsTitleChunk = new Text("Sample Order : Summary & Receipt").setFont(font).setFontSize(20.0f).setFontColor(mColorBlack);
            Paragraph mOrderDetailsTitleParagraph = new Paragraph(mOrderDetailsTitleChunk)
                    .setTextAlignment(TextAlignment.LEFT);
            document.add(mOrderDetailsTitleParagraph);

            // Adding Line Breakable Space....
            document.add(new Paragraph(""));
            // Adding Horizontal Line...
            document.add(lineSeparator);
            // Adding Line Breakable Space....
            document.add(new Paragraph(""));

            float columnWidth[]={280,280};
            Table table1=new Table(columnWidth);

            //table1=====


            //table1-----01
            table1.addCell(new Cell().add(new Paragraph("Order Id ").setBold()).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph("5465475496546465465")).setBorder(Border.NO_BORDER));

            //table1-----02
            table1.addCell(new Cell().add(new Paragraph("Order Time ").setBold()).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph("13 April 2022, 11:45 AM")).setBorder(Border.NO_BORDER));

            //table1-----03
            table1.addCell(new Cell().add(new Paragraph(" Name ").setBold()).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph("Ram Kharat")).setBorder(Border.NO_BORDER));

            //table1-----04
            table1.addCell(new Cell().add(new Paragraph(" Address ").setBold()).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph("Thane West")).setBorder(Border.NO_BORDER));

            //table1-----05
            table1.addCell(new Cell().add(new Paragraph(" Name ").setBold()).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph("Reshma Chavan")).setBorder(Border.NO_BORDER));

            //table1-----06
            table1.addCell(new Cell().add(new Paragraph(" Address ").setBold()).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph("Thane West")).setBorder(Border.NO_BORDER));

            //table1-----07
            table1.addCell(new Cell().add(new Paragraph(" Name ").setBold()).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph("Prathamesh Raut")).setBorder(Border.NO_BORDER));



            float columnWidth2[]={224,112,112,112};
            Table table2=new Table(columnWidth2);


            //table2-------01
            table2.addCell(new Cell().add(new Paragraph("Sample order").setFontColor(invoicewhite).setTextAlignment(TextAlignment.LEFT)).setBackgroundColor(invoiceGray).setBorder(Border.NO_BORDER));
            table2.addCell(new Cell().add(new Paragraph("No.").setFontColor(invoicewhite).setTextAlignment(TextAlignment.CENTER)).setBackgroundColor(invoiceGray).setBorder(Border.NO_BORDER));
            table2.addCell(new Cell().add(new Paragraph("Unit Price").setFontColor(invoicewhite).setTextAlignment(TextAlignment.CENTER)).setBackgroundColor(invoiceGray).setBorder(Border.NO_BORDER));
            table2.addCell(new Cell().add(new Paragraph("Total Price").setFontColor(invoicewhite).setTextAlignment(TextAlignment.CENTER)).setBackgroundColor(invoiceGray).setBorder(Border.NO_BORDER));

            //table2-------02
            table2.addCell(new Cell().add(new Paragraph("Prathamesh Raut").setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
            table2.addCell(new Cell().add(new Paragraph("5").setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER));
            table2.addCell(new Cell().add(new Paragraph("Rs 500").setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER));
            table2.addCell(new Cell().add(new Paragraph("Rs 500").setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER));

            //table2-------03
            table2.addCell(new Cell().add(new Paragraph("Pawan Patil").setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
            table2.addCell(new Cell().add(new Paragraph("5").setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER));
            table2.addCell(new Cell().add(new Paragraph("Rs 500").setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER));
            table2.addCell(new Cell().add(new Paragraph("Rs 500").setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER));


            //table2-------04
            table2.addCell(new Cell().add(new Paragraph("Reshma Chavan").setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
            table2.addCell(new Cell().add(new Paragraph("5").setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER));
            table2.addCell(new Cell().add(new Paragraph("Rs 500").setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER));
            table2.addCell(new Cell().add(new Paragraph("Rs 500").setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER));


            //table2-------05
            table2.addCell(new Cell().add(new Paragraph("Pratik Rane").setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
            table2.addCell(new Cell().add(new Paragraph("5").setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER));
            table2.addCell(new Cell().add(new Paragraph("Rs 500").setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER));
            table2.addCell(new Cell().add(new Paragraph("Rs 500").setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER));


            //table2-------06
            table2.addCell(new Cell().add(new Paragraph("Nilesh Patil").setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
            table2.addCell(new Cell().add(new Paragraph("5").setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER));
            table2.addCell(new Cell().add(new Paragraph("Rs 500").setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER));
            table2.addCell(new Cell().add(new Paragraph("Rs 500").setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER));





            float columnWidth3[]={448,112};
            Table table3=new Table(columnWidth3);


            //table3-------01
            table3.addCell(new Cell().add(new Paragraph("Sample Bill ").setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));
            table3.addCell(new Cell().add(new Paragraph("Rs 500").setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER));


            //table3-------01
            table3.addCell(new Cell().add(new Paragraph("Taxes and Charges ").setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));
            table3.addCell(new Cell().add(new Paragraph("Rs 300").setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER));



            //table3-------01
            table3.addCell(new Cell().add(new Paragraph("Discount ").setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));
            table3.addCell(new Cell().add(new Paragraph("Rs 600").setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER));



            //table3-------01
            table3.addCell(new Cell().add(new Paragraph("Coupon Discount ").setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));
            table3.addCell(new Cell().add(new Paragraph("Rs 800").setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER));



            //table3-------01
            table3.addCell(new Cell().add(new Paragraph("Convenience fee ").setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));
            table3.addCell(new Cell().add(new Paragraph("Rs 100").setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER));



            float columnWidth4[]={224,112,112,112};
            Table table4=new Table(columnWidth4);


            //table4-------01
            table4.addCell(new Cell().add(new Paragraph("").setFontColor(invoicewhite)).setBackgroundColor(invoiceGray).setBorder(Border.NO_BORDER));
            table4.addCell(new Cell().add(new Paragraph("").setFontColor(invoicewhite)).setBackgroundColor(invoiceGray).setBorder(Border.NO_BORDER));
            table4.addCell(new Cell().add(new Paragraph("Total").setFontColor(invoicewhite).setTextAlignment(TextAlignment.CENTER)).setBackgroundColor(invoiceGray).setBorder(Border.NO_BORDER));
            table4.addCell(new Cell().add(new Paragraph("Rs 5000").setFontColor(invoicewhite).setTextAlignment(TextAlignment.CENTER)).setBackgroundColor(invoiceGray).setBorder(Border.NO_BORDER));

            document.add(table1);
            document.add(new Paragraph("\n"));
            document.add(table2);
            document.add(new Paragraph(" "));
            document.add(lineSeparator);
            document.add(new Paragraph(" "));
            document.add(table3);
            document.add(new Paragraph("\n"));
            document.add(table4);
            document.add(new Paragraph("\n\n"));


            // Adding Title....
            Text termandcondition = new Text("Term & Conditions").setFont(font).setFontSize(20.0f).setFontColor(mColorBlack);
            Paragraph ptermandcondition = new Paragraph(termandcondition)
                    .setTextAlignment(TextAlignment.LEFT);
            document.add(ptermandcondition);

            Text termandcondition1 = new Text("1.Insurance coverage that pays agreed-on medical expenses up to a relatively low maximum. For example, an insurance maximum may be $50,000 lifetime benefit for each injury or sickness.").setFontColor(mColorBlack);
            Paragraph ptermandcondition1 = new Paragraph(termandcondition1)
                    .setTextAlignment(TextAlignment.LEFT);
            document.add(ptermandcondition1);

            Text termandcondition2 = new Text("2.Insurance coverage that pays agreed-on medical expenses up to a relatively low maximum. For example, an insurance maximum may be $50,000 lifetime benefit for each injury or sickness.").setFontColor(mColorBlack);
            Paragraph ptermandcondition2 = new Paragraph(termandcondition2)
                    .setTextAlignment(TextAlignment.LEFT);
            document.add(ptermandcondition2);


            Text termandcondition3 = new Text("3.Insurance coverage that pays agreed-on medical expenses up to a relatively low maximum. For example, an insurance maximum may be $50,000 lifetime benefit for each injury or sickness.").setFontColor(mColorBlack);
            Paragraph ptermandcondition3 = new Paragraph(termandcondition3)
                    .setTextAlignment(TextAlignment.LEFT);
            document.add(ptermandcondition3);


            Text termandcondition4 = new Text("4.Insurance coverage that pays agreed-on medical expenses up to a relatively low maximum. For example, an insurance maximum may be $50,000 lifetime benefit for each injury or sickness.").setFontColor(mColorBlack);
            Paragraph ptermandcondition4 = new Paragraph(termandcondition4)
                    .setTextAlignment(TextAlignment.LEFT);
            document.add(ptermandcondition4);


            Text termandcondition5 = new Text("5.Insurance coverage that pays agreed-on medical expenses up to a relatively low maximum. For example, an insurance maximum may be $50,000 lifetime benefit for each injury or sickness.").setFontColor(mColorBlack);
            Paragraph ptermandcondition5 = new Paragraph(termandcondition5)
                    .setTextAlignment(TextAlignment.LEFT);
            document.add(ptermandcondition5);


            Text termandcondition6 = new Text("6.Insurance coverage that pays agreed-on medical expenses up to a relatively low maximum. For example, an insurance maximum may be $50,000 lifetime benefit for each injury or sickness.").setFontColor(mColorBlack);
            Paragraph ptermandcondition6 = new Paragraph(termandcondition6)
                    .setTextAlignment(TextAlignment.LEFT);
            document.add(ptermandcondition6);

            float columnWidth5[]={50,250,260};
            Table table5=new Table(columnWidth5);


            Drawable d1=getDrawable(R.drawable.logo);
            Bitmap bitmap1=((BitmapDrawable)d1).getBitmap();
            ByteArrayOutputStream stream1=new ByteArrayOutputStream();
            bitmap1.compress(Bitmap.CompressFormat.PNG,100,stream1);
            byte[] bitmapData1=stream1.toByteArray();

            ImageData imageData1=ImageDataFactory.create(bitmapData1);
            Image image1=new Image(imageData1);
            image1.setHeight(120);




            Drawable d2=getDrawable(R.drawable.logo);
            Bitmap bitmap2=((BitmapDrawable)d2).getBitmap();
            ByteArrayOutputStream stream2=new ByteArrayOutputStream();
            bitmap2.compress(Bitmap.CompressFormat.PNG,100,stream2);
            byte[] bitmapData2=stream2.toByteArray();

            ImageData imageData2=ImageDataFactory.create(bitmapData2);
            Image image2=new Image(imageData2);
            image2.setHeight(120);

            table5.addCell(new Cell(3,1).add(image1).setBorder(Border.NO_BORDER));
            table5.addCell(new Cell().add(new Paragraph(" ")).setBorder(Border.NO_BORDER));
            table5.addCell(new Cell().add(new Paragraph(" ")).setBorder(Border.NO_BORDER));
            table5.addCell(new Cell().add(new Paragraph(" ")).setBorder(Border.NO_BORDER));
            table5.addCell(new Cell().add(new Paragraph(" ")).setBorder(Border.NO_BORDER));



            document.add(new Paragraph("\n"));
            document.add(table5);

            document.close();

            Toast.makeText(mContext, "Created... :)", Toast.LENGTH_SHORT).show();


        } catch (IOException e) {
            LOGE("createPdf: Error " + e.getLocalizedMessage());
        } catch (ActivityNotFoundException ae) {
            Toast.makeText(mContext, "No application found to open this file.", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == PermissionsActivity.PERMISSIONS_GRANTED) {
            Toast.makeText(mContext, "Permission Granted to Save", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "Permission not granted, Try again!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void createPDF(View view) {
        if (checker.lacksPermissions(REQUIRED_PERMISSION)) {
            PermissionsActivity.startActivityForResult(MainActivity.this, PERMISSION_REQUEST_CODE, REQUIRED_PERMISSION);
        } else {
            createPdf(dest);
        }
    }

    public void openPDF(View view) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    FileUtils.openFile(mContext, new File(dest));
                } catch (Exception e) {
                    Log.d("TAG", "run: ERror");
                }
            }
        }, 1000);
    }
}