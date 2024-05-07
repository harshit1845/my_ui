package com.example.makesurest.aggregration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makesurest.ApiClinet;
import com.example.makesurest.Helpers;
import com.example.makesurest.R;
import com.example.makesurest.adapter.CaseChildAdapter;
import com.example.makesurest.databinding.ActivityAggregationMainBinding;
import com.example.makesurest.model.CheckDuplicateRequest;
import com.example.makesurest.model.CheckDuplicateResponse;
import com.example.makesurest.model.PackagingCountRequest;
import com.example.makesurest.model.PackagingCountResponce;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AggregationMain extends AppCompatActivity {
    ActivityAggregationMainBinding binding;
    TextView comapnyNamee, batchId, errorLabel, label4, pcount, proName;
    String companyName, batchIdd, scanHGs, gtin, childCount, companyId, proId, proMatrixId, proMatrixId1, pcountt, cpak, ppak, batchName, caseid, gtinn, batchCode, gtinNumber, productName, dateSubstring, perentCountt, secondaryID;
    EditText scanHG;
    ArrayList ScannedArray;
    private RecyclerView recyclerView;
    String result , countChildPack;
    private RecyclerView.LayoutManager layoutManager;
    String parentCount,packging_level;
    ArrayList<String> qrCodesList = new ArrayList<>();
    String[] formats = new String[4];
    String gttin,batch;
    int endIndex;
    String GTIN = null;
    String BatchNo = null;
    String ExpDate = null;
    String UID = null;

    TextView loss,totalCounttv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_aggregation_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Child Barcode Scan");
        batchId = findViewById(R.id.batchId);
        proName = findViewById(R.id.proName);
        companyName = getIntent().getStringExtra("companyName");
        batchIdd = getIntent().getStringExtra("batchId");
        packging_level = getIntent().getStringExtra("norms");
        secondaryID = getIntent().getStringExtra("sId");
        pcount = findViewById(R.id.pCount);
        totalCounttv = findViewById(R.id.totalCounttv);
        label4 = findViewById(R.id.label4);
        scanHG = findViewById(R.id.scanHG);
        loss = findViewById(R.id.loss);
        errorLabel = findViewById(R.id.errorLabel);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        ScannedArray = new ArrayList();
        System.out.println("count" + childCount);
        companyId = getIntent().getStringExtra("companyId");
        parentCount = getIntent().getStringExtra("ct");
        System.out.println("myy" + parentCount);
        proId = getIntent().getStringExtra("proId");
        proMatrixId = getIntent().getStringExtra("proMatrixId");
        System.out.println("proMatrixId" + proMatrixId);
        proMatrixId1 = getIntent().getStringExtra("proMatrixId1");
        batchName = getIntent().getStringExtra("batchName");
        productName = getIntent().getStringExtra("productName");
        cpak = getIntent().getStringExtra("cPak");
        ppak = getIntent().getStringExtra("pPak");
        gtin = getIntent().getStringExtra("gtin");
        gtinn = getIntent().getStringExtra("gtinn");
        getCountChild();
        getCountParent();
        errorLabel.setVisibility(View.GONE);
        loss.setVisibility(View.GONE);

        System.out.println("gtin" + gtin);
        batchId.setText(batchName);
        proName.setText(productName);
        if (parentCount.equals(perentCountt)) {
            errorLabel.setText("Complete Scanning" + cpak + "to" + ppak);
            errorLabel.setBackgroundResource(R.color.green);
            scanHG.setEnabled(false);
        }

//        if (Integer.parseInt(parentCount) >= Integer.parseInt(pcountt)){
//            errorLabel.setText("Complete Scanning" +cpak+ "to" +ppak);
//            errorLabel.setBackgroundResource(R.color.green);
//            scanHG.setEnabled(false);
//        }

        scanHG.requestFocus();

        scanHG.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int inType = scanHG.getInputType(); // backup the input type
                scanHG.setInputType(InputType.TYPE_NULL); // disable soft input
                scanHG.onTouchEvent(event); // call native handler
                scanHG.setInputType(inType); // restore input type
                return true; // consume touch even
            }
        });


        scanHG.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if ((keyCode == EditorInfo.IME_ACTION_SEARCH ||
                        keyCode == EditorInfo.IME_ACTION_DONE ||
                        keyCode == EditorInfo.IME_ACTION_SEND ||
                        keyCode == EditorInfo.IME_ACTION_GO ||
                        keyCode == EditorInfo.IME_MASK_ACTION ||
                        (event.getAction() == KeyEvent.ACTION_DOWN) &&
                                (keyCode == KeyEvent.KEYCODE_ENTER)) &&
                        !event.isShiftPressed()) {
                    if (keyCode != KeyEvent.KEYCODE_BACK) {
                        binding.errorLabeleCv.setVisibility(View.VISIBLE);
                        result = "";
                        String barcodeData = scanHG.getText().toString().trim();
                        String _BarcodeData = result.replace(((char)29), '|');
                        System.out.println("fksdhishv"+_BarcodeData);
                        replace(barcodeData);
                        String message = checkBarcodeFormat(barcodeData);
                        if (message.startsWith("Barcode format is valid")) {
                            System.out.println(message);
                            scanHG.setText("");
                            carryProcessForDGFT();
                        } else {
                            scanHG.setText("");
                            System.out.println(message);
                            // Display an error message indicating that the barcode format is not correct
                            Toast.makeText(AggregationMain.this, "Barcode format is not correct", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                return false;
            }
        });
    }

    public String checkBarcodeFormat(String barcodeData) {
        String[] formats = new String[]{
                "i:01;v:gtin:14;i:17;v:exp:6;i:10;v:batch:.20;f:29;i:21;v:uid:.20;f:29",
                "i:01;v:gtin:14;i:17;v:exp:6;i:10;v:batch:.20;f:29;i:21;v:uid:.20",
                "i:01;v:gtin:14;i:21;v:uid:.20;f:29;i:91;v:crypt_code:.50;f:29;i:92;v:crypt_key:3;f:29",
                "i:01;v:gtin:14;i:21;v:uid:.20;f:29;i:91;v:ck91:.4;f:29;i:92;v:cs92:.50"
        };
        UIDDetails uidDetails = new UIDDetails(gttin,batch);
        for (String format : formats) {
            DecodeResult[] results = uidDetails.Decode(barcodeData, format, "");
            for (DecodeResult result : results) {

                // Check if the result corresponds to the GTIN segment
                if (result.getValue().equals("gtin")) {
                    // Set the GTIN value extracted from the barcode data
                    GTIN = result.getFoundValue();
                }
                // Check if the result corresponds to the batch segment
                else if (result.getValue().equals("batch")) {
                    // Set the batch number extracted from the barcode data
                    BatchNo = result.getFoundValue();
                }
                // Check if the result corresponds to the expiration date segment
                else if (result.getValue().equals("exp")) {
                    // Set the expiration date extracted from the barcode data
                    ExpDate = result.getFoundValue();
                }
                // Check if the result corresponds to the UID segment
                else if (result.getValue().equals("uid")) {
                    // Set the UID extracted from the barcode data
                    UID = result.getFoundValue();
                }
            }

// Print the extracted values
            System.out.println("GTIN: " + GTIN);
            System.out.println("Batch Number: " + BatchNo);
            System.out.println("Expiration Date: " + ExpDate);
            System.out.println("UID: " + UID);


            System.out.println("barcode : "+barcodeData);
            boolean isValidFormat = true;
            for (DecodeResult result : results) {
                if (!result.isResult()) {
                    isValidFormat = false;
                    break;
                }
            }
            if (isValidFormat) {

                String gtin = uidDetails.getGTIN();
                String batchNo = uidDetails.getBatchNo();
//                return "Barcode format is valid. GTIN: " + gtin + ", Batch Number: " + batchNo;

                return "Barcode format is valid";

            }
        }
        return "No matching format found for the barcode";
    }

    public class UIDDetails {
        private String GTIN;
        private String BatchNo;
        private String UID;
        private String ExpDate;
        private String BarcodeData;
        private String FailReason;
        private String MfgDate;
        private String CK91;
        private String CS92;

        public UIDDetails( String _GTIN,  String _Batch) {
            GTIN = _GTIN;
            BatchNo = _Batch;
            BarcodeData = "01" + _GTIN + "17" + ExpDate + "10" + BatchNo + ((char) 29) + "21" + UID;
        }

        public String getGTIN() {
            return GTIN;
        }

        public void setGTIN(String GTIN) {
            this.GTIN = GTIN;
        }

        public String getBatchNo() {
            return BatchNo;
        }

        public void setBatchNo(String batchNo) {
            BatchNo = batchNo;
        }

        public String getUID() {
            return UID;
        }

        public void setUID(String UID) {
            this.UID = UID;
        }

        public String getExpDate() {
            return ExpDate;
        }

        public void setExpDate(String expDate) {
            ExpDate = expDate;
        }

        public String getBarcodeData() {
            return BarcodeData;
        }

        public void setBarcodeData(String barcodeData) {
            BarcodeData = barcodeData;
        }

        public String getFailReason() {
            return FailReason;
        }

        public void setFailReason(String failReason) {
            FailReason = failReason;
        }

        public String getMfgDate() {
            return MfgDate;
        }

        public void setMfgDate(String mfgDate) {
            MfgDate = mfgDate;
        }

        public String getCK91() {
            return CK91;
        }

        public void setCK91(String CK91) {
            this.CK91 = CK91;
        }

        public String getCS92() {
            return CS92;
        }

        public void setCS92(String CS92) {
            this.CS92 = CS92;
        }

        public DecodeResult[] Decode(String data, String format, String RefBatch) {
            List<DecodeResult> lstDecoded = new ArrayList<>();
            int icurr = 0;
            String[] Variables = format.split(";");
            int refBatchLength = RefBatch != "" ? RefBatch.length() : 1;
            for (int i = 0; i <= Variables.length - 1; i++) {
                String item = Variables[i];

                if (icurr >= data.length())
                    break;
                String[] attributes = item.split(":");

                if (attributes[0].equals("i")) {
                    String FoundValue;
                    if (icurr >= 0 && icurr + attributes[1].length() <= data.length()) {
                        FoundValue = data.substring(icurr, icurr + attributes[1].length());
                    } else {
                        FoundValue = ""; // Handle out-of-range condition gracefully
                    }
                    if (attributes[1].equals(FoundValue)) {
                        lstDecoded.add(new DecodeResult(attributes[0], attributes[1], FoundValue, true, ""));
                    } else {
                        lstDecoded.add(new DecodeResult(attributes[0], attributes[1], FoundValue, false, "Identifier miss match"));
                    }
                    icurr += attributes[1].length();
                } else if (attributes[0].equals("f")) {
                    String FoundValue = data.substring(icurr, icurr + 1);
                    if (FoundValue.equals(Character.toString((char) Integer.parseInt(attributes[1])))) {
                        lstDecoded.add(new DecodeResult(attributes[0], attributes[1], attributes[1], true, ""));
                        icurr += 1;
                    } else {
                        int iasc = (int) (FoundValue.charAt(0));
                        lstDecoded.add(new DecodeResult(attributes[0], attributes[1], Integer.toString(iasc), false, "Function character not found"));
                    }
                } else if (attributes[0].equals("v")) {
                    if (attributes[2].contains(".")) {
                        String SubData = "";
                        if (Variables.length > i + 1 && Variables[i + 1].split(":")[0].equals("f") && data.substring(icurr).contains(Character.toString((char) Integer.parseInt(Variables[i + 1].split(":")[1])))) {
                            SubData = data.substring(icurr).split(Character.toString((char) Integer.parseInt(Variables[i + 1].split(":")[1])))[0];
                        } else if (Variables.length > i + 1 && Variables[i + 1].split(":")[0].equals("i") && data.substring(icurr).contains(Variables[i + 1].split(":")[1])) {
                            SubData = SplitWith(data.substring(icurr), Variables[i + 1].split(":")[1], (Variables[i].split(":")[1].equals("batch") ? refBatchLength : 1))[0];
                            if ((icurr + SubData.length() < data.length())) {
                                boolean tmpResult = CheckFerther(i + 1, Variables, data.substring(icurr + SubData.length()));
                                int iinc = 0;
                                if (!tmpResult) {
                                    while (!tmpResult & (icurr + SubData.length() + iinc + 1) < data.length()) {
                                        iinc += 1;
                                        tmpResult = CheckFerther(i + 1, Variables, data.substring(icurr + SubData.length() + iinc));
                                    }
                                }
                                if (tmpResult)
                                    SubData = data.substring(icurr, SubData.length() + iinc);
                            }
                        } else if (Variables.length > i + 2 && Variables[i + 2].split(":")[0].equals("i") && data.substring(icurr).contains(Variables[i + 2].split(":")[1])) {
                            SubData = SplitWith(data.substring(icurr), Variables[i + 2].split(":")[1], (Variables[i].split(":")[1].equals("batch") ? refBatchLength : 1))[0];
                            if ((icurr + SubData.length() < data.length())) {
                                boolean tmpResult = CheckFerther(i + 2, Variables, data.substring(icurr + SubData.length()));
                                int iinc = 0;
                                if (!tmpResult) {
                                    while (!tmpResult & (icurr + SubData.length() + iinc + 1) < data.length()) {
                                        iinc += 1;
                                        tmpResult = CheckFerther(i + 2, Variables, data.substring(icurr + SubData.length() + iinc));
                                    }
                                }
                                if (tmpResult)
                                     endIndex = Math.min(icurr + SubData.length() + iinc, data.length());
                                SubData = data.substring(icurr, endIndex);
                            }
                        } else
                            SubData = data.substring(icurr);

                        int LengthLimit = Integer.parseInt(attributes[2].replace(".", ""));

                        if (SubData.length() <= LengthLimit)
                            lstDecoded.add(new DecodeResult(attributes[0], attributes[1], SubData, true, ""));
                        else
                            lstDecoded.add(new DecodeResult(attributes[0], attributes[1], SubData, false, "Length of " + attributes[1] + " must be upto " + LengthLimit));

                        icurr += SubData.length();
                    } else {
                        lstDecoded.add(new DecodeResult(attributes[0], attributes[1], data.substring(icurr, icurr + Integer.parseInt(attributes[2])), true, ""));
                        icurr += Integer.parseInt(attributes[2]);
                    }
                }
            }

            return lstDecoded.toArray(new DecodeResult[0]);
        }

        private boolean CheckFerther(int StartIndex, String[] Variables, String RestString) {
            List<String> rest = new ArrayList<>();
            for (int index = StartIndex; index <= Variables.length - 1; index++)
                rest.add(Variables[index]);
            DecodeResult[] tempresult = Decode(RestString, String.join(";", rest.toArray(new String[0])), "");
            boolean bPass = true;
            for (DecodeResult result : tempresult) {
                if (!result.Type.equals("f") && !result.Result) {
                    bPass = false;
                    break;
                }
            }
            return bPass;
        }

        private String[] SplitWith(String Data, String SplitString, int RefLength) {
            String[] SData = new String[2];
            SData[0] = Data.substring(0, RefLength);
            SData[1] = Data.substring(RefLength);

            String tryBatch = SData[0];
            String tryuid = SData[1];
            boolean tryDone = true;
            while (!tryuid.startsWith(SplitString)) {
                if (tryuid.length() > SplitString.length()) {
                    tryBatch += Character.isLetterOrDigit(tryuid.charAt(0)) ? tryuid.charAt(0) : "";
                    tryuid = tryuid.substring(1);
                } else {
                    tryDone = false;
                    break;
                }
            }

            if (!tryDone) {
                tryBatch = SData[0];
                tryuid = SData[1];
                tryDone = true;
                while (!tryuid.startsWith(SplitString)) {
                    if (tryBatch.length() > SplitString.length()) {
                        tryuid = tryBatch.charAt(tryBatch.length() - 1) + tryuid;
                        tryBatch = tryBatch.substring(0, tryBatch.length() - 1);
                    } else {
                        tryDone = false;
                        break;
                    }
                }
            }

            if (tryDone) {
                SData[0] = tryBatch;
                SData[1] = tryuid.substring(2);
            }

            return SData;
        }

    }

    public class DecodeResult {
        public String Type;
        public String Value;
        public String FoundValue;
        public boolean Result;
        public String FailReason;

        public DecodeResult(String Type, String Value, String FoundValue, boolean Result, String FailReason) {
            this.Type = Type;
            this.Value = Value;
            this.FoundValue = FoundValue;
            this.Result = Result;
            this.FailReason = FailReason;
        }

        public String getType() {
            return Type;
        }

        public void setType(String type) {
            Type = type;
        }

        public String getValue() {
            return Value;
        }

        public void setValue(String value) {
            Value = value;
        }

        public String getFoundValue() {
            return FoundValue;
        }

        public void setFoundValue(String foundValue) {
            FoundValue = foundValue;
        }

        public boolean isResult() {
            return Result;
        }

        public void setResult(boolean result) {
            Result = result;
        }

        public String getFailReason() {
            return FailReason;
        }

        public void setFailReason(String failReason) {
            FailReason = failReason;
        }
    }



    public void carryProcessForDGFT() {
        scanHGs = scanHG.getText().toString().trim().replaceAll("^\\s+", "");
        errorLabel.setVisibility(View.VISIBLE);
        int sizearray = ScannedArray.size();
        System.out.println("HOLOGRAM " + scanHGs);
        System.out.println("size" + sizearray);
        System.out.println("HOLOGRAM NO" + ScannedArray);
        replace(scanHGs);
        Pattern pattern = Pattern.compile("01(\\d{14})");
        Matcher matcher = pattern.matcher(result);

        if (matcher.find()) {
            // Extract the GTIN value
            gtinNumber = matcher.group(1);

            System.out.println("GTIN Number: " + gtinNumber);
        } else {
            // No GTIN found
            System.out.println("No GTIN found in the barcode");
        }

        caseid = extractValueAfter("<GS>21", result);
        System.out.println("Serial Number:" + caseid);
        batchCode = extractBatchNumber(result);
        dateSubstring = extractDateFromBarcode(result);
        System.out.println("Date: " + dateSubstring);
        System.out.println("caseId:" + caseid);
        System.out.println("batch:" + batchCode);

        checkValidation();



    }


    public void checkValidation() {
        CheckDuplicateRequest checkDuplicateRequest = new CheckDuplicateRequest();
        checkDuplicateRequest.setProductId(proId);
        checkDuplicateRequest.setCompanyId(companyId);
        checkDuplicateRequest.setBatchId(batchIdd);
        checkDuplicateRequest.setExpirydate(ExpDate);
        checkDuplicateRequest.setIsscc("0");
        checkDuplicateRequest.setGtinNo(GTIN);
        checkDuplicateRequest.setUidValid(UID);
        checkDuplicateRequest.setProductMatrixId(proMatrixId);
        Call<List<CheckDuplicateResponse>> call = ApiClinet.getUserService().checkDuplicate(checkDuplicateRequest);
        call.enqueue(new Callback<List<CheckDuplicateResponse>>() {
            @Override
            public void onResponse(Call<List<CheckDuplicateResponse>> call, Response<List<CheckDuplicateResponse>> response) {
                if (response.isSuccessful()) {
                    List<CheckDuplicateResponse> checkDuplicateResponses = response.body();

                    String message = checkDuplicateResponses.get(0).getMessage();

                    if (message.contains("Valid Barcode")) {
                        if (!qrCodesList.contains(UID)) {
                            binding.caseChildCv.setVisibility(View.VISIBLE);
                            binding.placeholder.setVisibility(View.GONE);
                            binding.syncNowBtn.setVisibility(View.VISIBLE);
                            Collections.reverse(qrCodesList);
                            qrCodesList.add(UID);
                            Collections.reverse(qrCodesList);
                            //Toast.makeText(CreateCase.this, "Valid barcode", Toast.LENGTH_SHORT).show();
                            loss.setVisibility(View.VISIBLE);
                            scanHG.setText("");
                            String qr = String.valueOf(qrCodesList.size());
                            label4.setText(qr);
                            totalCounttv.setText(countChildPack);
                            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaa" + qrCodesList.size());
                            recyclerView.setHasFixedSize(true);
                            layoutManager = new LinearLayoutManager(AggregationMain.this);
                            recyclerView.setLayoutManager(layoutManager);
                            CaseChildAdapter adapter = new CaseChildAdapter(getBaseContext(), qrCodesList);
                            recyclerView.setAdapter(adapter);

                            errorLabel.setText("PASS");
                            Helpers.ScanOk();
                            binding.errorLabeleCv.setBackgroundResource(R.drawable.err_label_green_bg);
                            errorLabel.setTextColor(getResources().getColor(R.color.colorGreen));
                            lossButtonClicked();
                            if (qrCodesList.size() == Integer.parseInt(countChildPack)) {
                                errorLabel.setText(countChildPack + "Hologram scanning Completed");
                                binding.errorLabeleCv.setBackgroundResource(R.drawable.err_label_green_bg);
                                errorLabel.setTextColor(getResources().getColor(R.color.colorGreen));
                                SendData();
                                Helpers.ScanOk();
                                Toast.makeText(AggregationMain.this, "Please scan the case no.", Toast.LENGTH_SHORT).show();
                                scanHG.setEnabled(false);
                            }
                        } else {
                            errorLabel.setText("UID is Already Scanned");
                            binding.errorLabeleCv.setBackgroundResource(R.drawable.err_label_red_bg);
                            binding.errorLabel.setTextColor(getResources().getColor(R.color.red));
                            Helpers.ScanError();
                            scanHG.setText("");
                            Toast.makeText(AggregationMain.this, "Not valid barcode", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        errorLabel.setText(message);
                        binding.errorLabeleCv.setBackgroundResource(R.drawable.err_label_red_bg);
                        binding.errorLabel.setTextColor(getResources().getColor(R.color.red));
                        Helpers.ScanError();
                        scanHG.setText("");
                    }

                }
            }

            @Override
            public void onFailure(Call<List<CheckDuplicateResponse>> call, Throwable t) {
                // Handle the error.
            }
        });
    }

    private void lossButtonClicked() {
        loss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (qrCodesList.size() != Integer.parseInt(countChildPack)) {

                    int sizeDifference = qrCodesList.size() - Integer.parseInt(countChildPack);
                    errorLabel.setText(sizeDifference + "Hologram Lose");
                    binding.errorLabeleCv.setBackgroundResource(R.drawable.err_label_red_bg);
                    binding.errorLabel.setTextColor(getResources().getColor(R.color.red));
                    SendData();
//                  Helpers.ScanOk();
                }

            }
        });
    }


    public static String extractDateFromBarcode(String barcode) {
        // Check if barcode string has enough characters for the group separator and date
        if (barcode.length() >= 23) {
            // Extract the substring representing the date (6 digits)
            return barcode.substring(18, 18 + 6);
        } else {
            // Handle case where barcode string does not contain enough characters for the date
            return "Date not found";
        }
    }


    public String extractValueAfter(String substring, String input) {
        int index = input.indexOf(substring);
        if (index != -1) {
            // Found the substring, extract the value after it
            return input.substring(index + substring.length());
        } else {
            // Substring not found, return an empty string or handle accordingly
            return "";
        }
    }


    public String replace(String data) {
        List<Integer> list = new ArrayList<>();
        char temp;
        for (int i = 0; i < data.length(); i++) {
            temp = data.charAt(i);
            if (temp == 0x1d) {
                list.add(i);
                //Log.e(TAG,"i:"+i);
            }
        }
        if (list == null || list.size() == 0) {
            return data;
        }

        int startIndex = 0;
        for (int i = 0; i < list.size(); i++) {
            // Log.e(TAG,"list.get(i):"+list.get(i));
            result += data.substring(startIndex, list.get(i));
            startIndex = list.get(i) + 1;
            result += "<GS>";
        }
        result += data.substring(startIndex);

        System.out.println("replace barcode :" + result);
        return result;
    }

    private String extractBatchNumber(String input) {
        // Assuming the structure: Identifier (2 letters) + GTIN (14 digits) + Group Separator (2 digits) + Batch Info
        int identifierLength = 2;
        int gtinLength = 14;
        int groupSeparatorLength = 2;

        // Calculate the index where the group separator should start (after GTIN)
        int gsIndex = identifierLength + gtinLength;

        // Find the index of "10" starting after the group separator
        int startIndex = input.indexOf("10", gsIndex);

        // Find the index of "<GS>"
        int gsEndIndex = input.indexOf("<GS>", gsIndex);

        // Validate that "10" is found before "<GS>"
        if (startIndex != -1 && gsEndIndex != -1 && startIndex < gsEndIndex) {
            // Found "10" before "<GS>", extract the batch number after the group separator
            return input.substring(startIndex + 2, gsEndIndex);
        } else {
            // "10" not found before "<GS>", or "<GS>" not found, return an empty string or handle accordingly
            return "";
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void SendData() { // function used to send the intent with data
        Intent intent = new Intent(AggregationMain.this, AggregationComplete.class);
        intent.putExtra("ScannedArray", qrCodesList);
        intent.putExtra("batchId", batchIdd);
        intent.putExtra("companyId", companyId);
        intent.putExtra("proId", proId);
        intent.putExtra("proMatrixId", proMatrixId);
        intent.putExtra("proMatrixId1", proMatrixId1);
        intent.putExtra("cCount", countChildPack);
        intent.putExtra("sId", secondaryID);
        intent.putExtra("cPak", cpak);
        intent.putExtra("pPak", ppak);
        intent.putExtra("batchName", batchName);
        intent.putExtra("productName", productName);
        intent.putExtra("gtinn", gtinn);
        intent.putExtra("gtin", gtin);
        intent.putExtra("exp_date", dateSubstring);

        startActivity(intent);
        finish();
    }


    protected void onResume() {

        super.onResume();

        scanHG.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int inType = scanHG.getInputType(); // backup the input type
                scanHG.setInputType(InputType.TYPE_NULL); // disable soft input
                scanHG.onTouchEvent(event); // call native handler
                scanHG.setInputType(inType); // restore input type
                return true; // consume touch even
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();

        scanHG.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int inType = scanHG.getInputType(); // backup the input type
                scanHG.setInputType(InputType.TYPE_NULL); // disable soft input
                scanHG.onTouchEvent(event); // call native handler
                scanHG.setInputType(inType); // restore input type
                return true; // consume touch even
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();


        scanHG.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int inType = scanHG.getInputType(); // backup the input type
                scanHG.setInputType(InputType.TYPE_NULL); // disable soft input
                scanHG.onTouchEvent(event); // call native handler
                scanHG.setInputType(inType); // restore input type
                return true; // consume touch even
            }
        });
    }


    public void getCountParent() {
        PackagingCountRequest packagingCountRequest = new PackagingCountRequest();
        packagingCountRequest.setCount(secondaryID);

        Call<List<PackagingCountResponce>> call = ApiClinet.getUserService().getPackagingCount(packagingCountRequest);
        call.enqueue(new Callback<List<PackagingCountResponce>>() {
            @Override
            public void onResponse(Call<List<PackagingCountResponce>> call, Response<List<PackagingCountResponce>> response) {
                if (response.isSuccessful()) {
                    // Parse the response from the API into a list of ProductResponse objects.
                    List<PackagingCountResponce> packagingCountResponces = response.body();

                    perentCountt = packagingCountResponces.get(0).getPackgingCount();

                    System.out.println("perentCount" + perentCountt);
                    pcount.setText(perentCountt);
                    binding.labelCase.setText(parentCount);


                }
            }

            @Override
            public void onFailure(Call<List<PackagingCountResponce>> call, Throwable t) {
                // Handle the error.
            }
        });


    }


    public void getCountChild() {
        PackagingCountRequest packagingCountRequest = new PackagingCountRequest();
        packagingCountRequest.setCount(proMatrixId);

        Call<List<PackagingCountResponce>> call = ApiClinet.getUserService().getPackagingCount(packagingCountRequest);
        call.enqueue(new Callback<List<PackagingCountResponce>>() {
            @Override
            public void onResponse(Call<List<PackagingCountResponce>> call, Response<List<PackagingCountResponce>> response) {
                if (response.isSuccessful()) {
                    // Parse the response from the API into a list of ProductResponse objects.
                    List<PackagingCountResponce> packagingCountResponces = response.body();

                    countChildPack = packagingCountResponces.get(0).getPackgingCount();

//                    for (PackagingCountResponce packagingCountResponce : packagingCountResponces) {
//                        childPackageCount.add(packagingCountResponce.getChildCount());
                    System.out.println("childCount" + countChildPack);
//
//
//
//                    }


                }
            }

            @Override
            public void onFailure(Call<List<PackagingCountResponce>> call, Throwable t) {
                // Handle the error.
            }
        });


    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        finish();
    }





}