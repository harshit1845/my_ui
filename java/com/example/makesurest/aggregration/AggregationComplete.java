package com.example.makesurest.aggregration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makesurest.ApiClinet;
import com.example.makesurest.R;
import com.example.makesurest.databinding.ActivityAggregationCompleteBinding;
import com.example.makesurest.model.AggregationRequest;
import com.example.makesurest.model.AggregationResponce;
import com.example.makesurest.model.CheckDuplicateRequest;
import com.example.makesurest.model.CheckDuplicateResponse;
import com.example.makesurest.model.CountCaseResponce;
import com.example.makesurest.model.CountRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AggregationComplete extends AppCompatActivity {
    ConstraintLayout errorLabeleCv;
    EditText scanNo;
    TextView batchId,errorLabel,proName,packSize;
    int batchIdd,companyId,proId;
    String scanNos,proMatrixId,proMatrixId1,cCount,pCount,myCount,cPak,pPak,batchName,message,scanArrayWithoutBrackets,gtinn,caseid,gtinNumber,gtin,batchCode,productName,exp_date,companyNumber;
    ArrayList ScannedArray,ScannedArrayy;
    String sId,strwithoutspace;
    private int countParent = 0;
    String result = "";
    String gttin,batch;
    int endIndex;
    String GTIN = null;
    String BatchNo = null;
    String ExpDate = null;
    String UID = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this,R.layout.activity_aggregation_complete);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Parent Barcode Scan");
        scanNo = findViewById(R.id.scanNo);
        batchId = findViewById(R.id.label1);
        packSize = findViewById(R.id.packSize);
        proName = findViewById(R.id.proName);
        errorLabeleCv = findViewById(R.id.errorLabeleCv);
        batchIdd = Integer.parseInt(getIntent().getStringExtra("batchId"));
        ScannedArray = getIntent().getStringArrayListExtra("ScannedArray");
        System.out.println("scanArray"+ScannedArray);

        companyId = Integer.parseInt(getIntent().getStringExtra("companyId"));
        proId = Integer.parseInt(getIntent().getStringExtra("proId"));
        proMatrixId = getIntent().getStringExtra("proMatrixId");
        proMatrixId1 = getIntent().getStringExtra("proMatrixId1");
        System.out.println("port"+proMatrixId1);
        cCount = getIntent().getStringExtra("cCount");
        pCount = getIntent().getStringExtra("pcount");
        cPak = getIntent().getStringExtra("cPak");
        pPak = getIntent().getStringExtra("pPak");
        gtinn = getIntent().getStringExtra("gtinn");
        gtin = getIntent().getStringExtra("gtin");

        batchName = getIntent().getStringExtra("batchName");
        productName = getIntent().getStringExtra("productName");
        exp_date = getIntent().getStringExtra("exp_date");
        sId = getIntent().getStringExtra("sId");
        errorLabel = findViewById(R.id.errorLabel);
        batchId.setText(batchName);
        proName.setText(productName);
        System.out.println("cCount"+cCount);
        packSize.setText(cCount);
        String scanArrayStr = String.valueOf(ScannedArray);
        //Remove Bracket From Array
        scanArrayWithoutBrackets = scanArrayStr.toString().substring(1,scanArrayStr.length()-1);
        System.out.println("Array without brackets: "+scanArrayWithoutBrackets);

         strwithoutspace = scanArrayWithoutBrackets;
        String newStr = strwithoutspace.replaceAll("\\s", "");
        System.out.println(newStr);

        errorLabel.setVisibility(View.GONE);

        scanNo.requestFocus();

        // used for blocking the keyboard
        //for mc 36 and tc 21

        scanNo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int inType = scanNo.getInputType(); // backup the input type
                scanNo.setInputType(InputType.TYPE_NULL); // disable soft input
                scanNo.onTouchEvent(event); // call native handler
                scanNo.setInputType(inType); // restore input type
                return true; // consume touch even
            }
        });

        scanNo.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                try {
                    if ((keyCode == EditorInfo.IME_ACTION_SEARCH ||
                            keyCode == EditorInfo.IME_ACTION_DONE ||
                            keyCode == EditorInfo.IME_ACTION_SEND ||
                            keyCode == EditorInfo.IME_ACTION_GO ||
                            keyCode == EditorInfo.IME_MASK_ACTION ||
                            (event.getAction() == KeyEvent.ACTION_DOWN) &&
                                    (keyCode == KeyEvent.KEYCODE_ENTER)) &&
                            !event.isShiftPressed()) {

                        if (keyCode != KeyEvent.KEYCODE_BACK) {
                            errorLabeleCv.setVisibility(View.VISIBLE);
                            result = "";
                                carryProcess();
                        }
                    }

                } catch (NumberFormatException e) {
                    throw new RuntimeException(e);
                }
                return false;
            }
        });
    }

    public String checkBarcodeFormat(String barcodeData) {
        String[] formats = new String[]{
                "i:01;v:gtin:14;i:17;v:exp:6;i:10;v:batch:.20;f:29;i:21;v:uid:.20;f:29",
                "i:01;v:gtin:14;i:17;v:exp:6;i:10;v:batch:.20;f:29;i:21;v:uid:.20",
                "i:01;v:gtin:14;i:21;v:uid:.20;f:29;i:91;v:crypt_code:.50;f:29;i:92;v:crypt_key:3;f:29"
        };
        AggregationComplete.UIDDetails uidDetails = new AggregationComplete.UIDDetails(gttin,batch);
        for (String format : formats) {
            AggregationComplete.DecodeResult[] results = uidDetails.Decode(barcodeData, format, "");
            for (AggregationComplete.DecodeResult result : results) {


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


            for (AggregationComplete.DecodeResult result : results) {
                // Check if the result corresponds to the batch segment
                if (result.getValue().equals("batch")) {
                    // Set the GTIN value extracted from the barcode data
                    String  BATCH = result.getFoundValue();
                    System.out.println(BATCH+"BBBBB");
                    break; // Exit the loop once the GTIN value is found
                }
            }

            System.out.println("barcode : "+barcodeData);
            boolean isValidFormat = true;
            for (AggregationComplete.DecodeResult result : results) {
                if (!result.isResult()) {
                    isValidFormat = false;
                    break;
                }
            }
            if (isValidFormat) {

                String gtin = uidDetails.getGTIN();
                String batchNo = uidDetails.getBatchNo();
                return "Barcode format is valid. GTIN: " + gtin + ", Batch Number: " + batchNo;

//                return "Barcode format is valid";

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

        public AggregationComplete.DecodeResult[] Decode(String data, String format, String RefBatch) {
            List<AggregationComplete.DecodeResult> lstDecoded = new ArrayList<>();
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
                        lstDecoded.add(new AggregationComplete.DecodeResult(attributes[0], attributes[1], FoundValue, true, ""));
                    } else {
                        lstDecoded.add(new AggregationComplete.DecodeResult(attributes[0], attributes[1], FoundValue, false, "Identifier miss match"));
                    }
                    icurr += attributes[1].length();
                } else if (attributes[0].equals("f")) {
                    String FoundValue = data.substring(icurr, icurr + 1);
                    if (FoundValue.equals(Character.toString((char) Integer.parseInt(attributes[1])))) {
                        lstDecoded.add(new AggregationComplete.DecodeResult(attributes[0], attributes[1], attributes[1], true, ""));
                        icurr += 1;
                    } else {
                        int iasc = (int) (FoundValue.charAt(0));
                        lstDecoded.add(new AggregationComplete.DecodeResult(attributes[0], attributes[1], Integer.toString(iasc), false, "Function character not found"));
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
                            lstDecoded.add(new AggregationComplete.DecodeResult(attributes[0], attributes[1], SubData, true, ""));
                        else
                            lstDecoded.add(new AggregationComplete.DecodeResult(attributes[0], attributes[1], SubData, false, "Length of " + attributes[1] + " must be upto " + LengthLimit));

                        icurr += SubData.length();
                    } else {
                        lstDecoded.add(new AggregationComplete.DecodeResult(attributes[0], attributes[1], data.substring(icurr, icurr + Integer.parseInt(attributes[2])), true, ""));
                        icurr += Integer.parseInt(attributes[2]);
                    }
                }
            }

            return lstDecoded.toArray(new AggregationComplete.DecodeResult[0]);
        }

        private boolean CheckFerther(int StartIndex, String[] Variables, String RestString) {
            List<String> rest = new ArrayList<>();
            for (int index = StartIndex; index <= Variables.length - 1; index++)
                rest.add(Variables[index]);
            AggregationComplete.DecodeResult[] tempresult = Decode(RestString, String.join(";", rest.toArray(new String[0])), "");
            boolean bPass = true;
            for (AggregationComplete.DecodeResult result : tempresult) {
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






    public void carryProcess() {
        scanNos = scanNo.getText().toString().trim().replace(" ", "");
        errorLabel.setVisibility(View.VISIBLE);
        AddGroupSeprater(scanNos);
//        Pattern pattern = Pattern.compile("01(\\d{14})");
//        Matcher matcher = pattern.matcher(scanNos);
        caseid =  extractValueAfter("<GS>21", result);
        System.out.println("Serial Number: " + caseid);
//        batchCode = extractBatchNumber(result);
        if (pPak.contains("Tertiary")){
            ValidateTertiaryBarcode();

        }
        else {


            String message = checkBarcodeFormat(scanNos);
            if (message.startsWith("Barcode format is valid")) {
                System.out.println(message);
                scanNo.setText("");
                ValidateBarcode();
            } else {
                scanNo.setText("");
                // Display an error message indicating that the barcode format is not correct
                Toast.makeText(AggregationComplete.this, "Barcode format is not correct", Toast.LENGTH_SHORT).show();
            }



        }
    }


    private static String extractValueAfter(String substring, String input) {
        int index = input.indexOf(substring);
        if (index != -1) {
            // Found the substring, extract the value after it
            return input.substring(index + substring.length());
        } else {
            // Substring not found, return an empty string or handle accordingly
            return "";
        }
    }


    private String AddGroupSeprater(String data){
        List<Integer> list = new ArrayList<>();
        char temp;
        for(int i=0; i<data.length();i++){
            temp = data.charAt(i);
            if(temp == 0x1d){
                list.add(i);
                //Log.e(TAG,"i:"+i);
            }
        }
        if(list == null || list.size() == 0){
            return data;
        }

        int startIndex= 0;
        for(int i=0; i<list.size(); i++){
            // Log.e(TAG,"list.get(i):"+list.get(i));
            result += data.substring(startIndex,list.get(i));
            startIndex = list.get(i)+1;
            result +="<GS>";
        }
        result += data.substring(startIndex);

        System.out.println("replace barcode :"+result);
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

    //check validation of barcode is valid or not

    public void ValidateTertiaryBarcode() {
        CheckDuplicateRequest checkDuplicateRequest = new CheckDuplicateRequest();
        checkDuplicateRequest.setProductId(String.valueOf(proId));
        checkDuplicateRequest.setCompanyId(String.valueOf(companyId));
        checkDuplicateRequest.setBatchId(String.valueOf(batchIdd));
        checkDuplicateRequest.setExpirydate(exp_date);
        checkDuplicateRequest.setIsscc("1");
        checkDuplicateRequest.setGtinNo(gtinn);
        checkDuplicateRequest.setUidValid(scanNos);
        checkDuplicateRequest.setProductMatrixId(proMatrixId1);

        Call<List<CheckDuplicateResponse>> call = ApiClinet.getUserService().checkDuplicate(checkDuplicateRequest);
        call.enqueue(new Callback<List<CheckDuplicateResponse>>() {
            @Override
            public void onResponse(Call<List<CheckDuplicateResponse>> call, Response<List<CheckDuplicateResponse>> response) {
                if (response.isSuccessful()) {
                    List<CheckDuplicateResponse> checkDuplicateResponses = response.body();
                    String message = checkDuplicateResponses.get(0).getMessage();
                    if (message.contains("Valid SSCC Barcode")) {
                        if (ScannedArray.size() != Integer.parseInt(cCount)) {
                            TeartiryBarcodeWithLoos();
                        }else {
                            TeartiryBarcodeWithoutLoos();
                        }

                    }
                    else {
                        scanNo.setText("");
                        errorLabel.setText(message);
                        errorLabeleCv.setBackgroundResource(R.drawable.err_label_red_bg);
                        errorLabel.setTextColor(getResources().getColor(R.color.red));

                    }
                }
            }
            @Override
            public void onFailure(Call<List<CheckDuplicateResponse>> call, Throwable t) {
                // Handle the error.
            }
        });
    }


    public void ValidateBarcode() {
        CheckDuplicateRequest checkDuplicateRequest = new CheckDuplicateRequest();
        checkDuplicateRequest.setProductId(String.valueOf(proId));
        checkDuplicateRequest.setCompanyId(String.valueOf(companyId));
        checkDuplicateRequest.setBatchId(String.valueOf(batchIdd));
        checkDuplicateRequest.setExpirydate(ExpDate);
        checkDuplicateRequest.setIsscc("0");
        checkDuplicateRequest.setGtinNo(GTIN);
        checkDuplicateRequest.setUidValid(UID);
        checkDuplicateRequest.setProductMatrixId(proMatrixId1);

        Call<List<CheckDuplicateResponse>> call = ApiClinet.getUserService().checkDuplicate(checkDuplicateRequest);
        call.enqueue(new Callback<List<CheckDuplicateResponse>>() {
            @Override
            public void onResponse(Call<List<CheckDuplicateResponse>> call, Response<List<CheckDuplicateResponse>> response) {
                if (response.isSuccessful()) {
                    List<CheckDuplicateResponse> checkDuplicateResponses = response.body();

                    String message = checkDuplicateResponses.get(0).getMessage();
                    if (message.contains("Valid Barcode")) {
                        if (ScannedArray.size() != Integer.parseInt(cCount)) {
                            AggregationWithLoos();
                        } else {
                            AggregationWithoutLoos();
                        }
                    }
                    else {
                        scanNo.setText("");
                        errorLabel.setText(message);
                        errorLabeleCv.setBackgroundResource(R.drawable.err_label_red_bg);
                        errorLabel.setTextColor(getResources().getColor(R.color.red));
                    }
                }
            }
            @Override
            public void onFailure(Call<List<CheckDuplicateResponse>> call, Throwable t) {
                // Handle the error.
            }
        });
    }

    public void AggregationWithoutLoos() {
        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();
        JsonObject jsonObject1 = new JsonObject();
        JsonObject jsonObject2= new JsonObject();
        JsonObject jsonObject3 = new JsonObject();
        JsonObject jsonObject4 = new JsonObject();
        JsonObject jsonObject5 = new JsonObject();
        JsonObject jsonObject6 = new JsonObject();
        JsonArray jsonObject7 = new JsonArray();
        JsonObject jsonObject8 = new JsonObject();

        jsonObject.addProperty("company_id", companyId);
        jsonObject1.addProperty("prod_id", proId);
        jsonObject2.addProperty("batchid", batchIdd);
        jsonObject3.addProperty("is_sscc", 0);
        jsonObject4.addProperty("Loose", 0);
        jsonObject5.addProperty("product_matrix_id",proMatrixId );
        jsonObject8.addProperty("product_matrix_id", proMatrixId1);
        jsonObject6.addProperty("parent", UID);
        JsonObject uidJsonObject = new JsonObject();
        JsonArray childJsonArray = new JsonArray();
        childJsonArray.add(strwithoutspace);
        uidJsonObject.add("child", childJsonArray);
        jsonObject7.add(uidJsonObject);
        JsonObject wrapperJsonObject1 = new JsonObject();
        wrapperJsonObject1.add("UID", jsonObject7);

        JsonArray jsonArray = new JsonArray();
        jsonArray.add(jsonObject);
        jsonArray.add(jsonObject1);
        jsonArray.add(jsonObject2);
        jsonArray.add(jsonObject3);
        jsonArray.add(jsonObject4);
        jsonArray.add(jsonObject5);
        jsonArray.add(jsonObject8);
        jsonArray.add(jsonObject6);
        jsonArray.add(wrapperJsonObject1);
//        JsonObject wrapperJsonObject = new JsonObject();
//        wrapperJsonObject.add("jsonContent", jsonArray);

        String jsonContent = gson.toJson(jsonArray);
        AggregationRequest aggregationRequest = new AggregationRequest();
        aggregationRequest.setJsonContent(jsonContent);
        System.out.println("bulk"+jsonContent);
        System.out.println("companyId"+companyId);

        Call<List<AggregationResponce>> call = ApiClinet.getUserService().aggregationApi(aggregationRequest);
        call.enqueue(new Callback<List<AggregationResponce>>() {
            @Override
            public void onResponse(Call<List<AggregationResponce>> call, Response<List<AggregationResponce>> response) {
                List<AggregationResponce> aggregationResponces = response.body();

                if (response.isSuccessful()) {
                    message = aggregationResponces.get(0).getMessage();
                        System.out.println("message" + message);
                        System.out.println("SUCCESSFULLY API CALL");
                        if (message.equals("Aggregation successfull....")) {
                            getDispatchDetails();
                        }
                        else {
                            errorLabel.setText(message);
                            errorLabeleCv.setBackgroundResource(R.drawable.err_label_red_bg);
                            errorLabel.setTextColor(getResources().getColor(R.color.red));
                            scanNo.setText("");
                        }
                }
            }
            @Override
            public void onFailure(Call<List<AggregationResponce>> call, Throwable t) {
                errorLabel.setText("ERROR");
                errorLabeleCv.setBackgroundResource(R.drawable.err_label_red_bg);
                errorLabel.setTextColor(getResources().getColor(R.color.red));
                scanNo.setText("");
            }
        });
    }

    public void AggregationWithLoos() {
        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();
        JsonObject jsonObject1 = new JsonObject();
        JsonObject jsonObject2= new JsonObject();
        JsonObject jsonObject3 = new JsonObject();
        JsonObject jsonObject4 = new JsonObject();
        JsonObject jsonObject5 = new JsonObject();
        JsonObject jsonObject6 = new JsonObject();
        JsonArray jsonObject7 = new JsonArray();
        JsonObject jsonObject8 = new JsonObject();

        jsonObject.addProperty("company_id", companyId);
        jsonObject1.addProperty("prod_id", proId);
        jsonObject2.addProperty("batchid", batchIdd);
        jsonObject3.addProperty("is_sscc", 0);
        jsonObject4.addProperty("Loose", 1);
        jsonObject5.addProperty("product_matrix_id",proMatrixId );
        jsonObject8.addProperty("product_matrix_id", proMatrixId1);
        jsonObject6.addProperty("parent", UID);
        JsonObject uidJsonObject = new JsonObject();
        JsonArray childJsonArray = new JsonArray();

        childJsonArray.add(strwithoutspace);
        uidJsonObject.add("child", childJsonArray);
        jsonObject7.add(uidJsonObject);

        JsonObject wrapperJsonObject1 = new JsonObject();
        wrapperJsonObject1.add("UID", jsonObject7);

        JsonArray jsonArray = new JsonArray();
        jsonArray.add(jsonObject);
        jsonArray.add(jsonObject1);
        jsonArray.add(jsonObject2);
        jsonArray.add(jsonObject3);
        jsonArray.add(jsonObject4);
        jsonArray.add(jsonObject5);
        jsonArray.add(jsonObject8);
        jsonArray.add(jsonObject6);
        jsonArray.add(wrapperJsonObject1);

        String jsonContent = gson.toJson(jsonArray);
        AggregationRequest aggregationRequest = new AggregationRequest();
        aggregationRequest.setJsonContent(jsonContent);
        System.out.println("bulk"+jsonContent);
        System.out.println("companyId"+companyId);


        Call<List<AggregationResponce>> call = ApiClinet.getUserService().aggregationApi(aggregationRequest);
        call.enqueue(new Callback<List<AggregationResponce>>() {
            @Override
            public void onResponse(Call<List<AggregationResponce>> call, Response<List<AggregationResponce>> response) {
                List<AggregationResponce> aggregationResponces = response.body();

                if (response.isSuccessful()) {
                    message = aggregationResponces.get(0).getMessage();

                    System.out.println("message" + message);
                    System.out.println("SUCCESSFULLY API CALL");

                    if (message.equals("Aggregation successfull....")) {
                        getDispatchDetails();
                    }
                    else {
                        errorLabel.setText("Duplicate Barcode");
                        errorLabeleCv.setBackgroundResource(R.drawable.err_label_red_bg);
                        errorLabel.setTextColor(getResources().getColor(R.color.red));
                        scanNo.setText("");
                    }

                }
            }


            @Override
            public void onFailure(Call<List<AggregationResponce>> call, Throwable t) {
                // Handle the error.
            }
        });
    }


    public void TeartiryBarcodeWithLoos() {
        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();
        JsonObject jsonObject1 = new JsonObject();
        JsonObject jsonObject2= new JsonObject();
        JsonObject jsonObject3 = new JsonObject();
        JsonObject jsonObject4 = new JsonObject();
        JsonObject jsonObject5 = new JsonObject();
        JsonObject jsonObject6 = new JsonObject();
        JsonArray jsonObject7 = new JsonArray();
        JsonObject jsonObject8 = new JsonObject();

        jsonObject.addProperty("company_id", companyId);
        jsonObject1.addProperty("prod_id", proId);
        jsonObject2.addProperty("batchid", batchIdd);
        jsonObject3.addProperty("is_sscc", 1);
        jsonObject4.addProperty("Loose", 1);
        jsonObject5.addProperty("product_matrix_id",proMatrixId );
        jsonObject8.addProperty("product_matrix_id", proMatrixId1);
        jsonObject6.addProperty("parent", scanNos);
        JsonObject uidJsonObject = new JsonObject();
        JsonArray childJsonArray = new JsonArray();

        childJsonArray.add(strwithoutspace);
        uidJsonObject.add("child", childJsonArray);
        jsonObject7.add(uidJsonObject);

        JsonObject wrapperJsonObject1 = new JsonObject();
        wrapperJsonObject1.add("UID", jsonObject7);

        JsonArray jsonArray = new JsonArray();
        jsonArray.add(jsonObject);
        jsonArray.add(jsonObject1);
        jsonArray.add(jsonObject2);
        jsonArray.add(jsonObject3);
        jsonArray.add(jsonObject4);
        jsonArray.add(jsonObject5);
        jsonArray.add(jsonObject8);
        jsonArray.add(jsonObject6);
        jsonArray.add(wrapperJsonObject1);
//        JsonObject wrapperJsonObject = new JsonObject();
//        wrapperJsonObject.add("jsonContent", jsonArray);

        String jsonContent = gson.toJson(jsonArray);
        AggregationRequest aggregationRequest = new AggregationRequest();
        aggregationRequest.setJsonContent(jsonContent);
        System.out.println("bulk"+jsonContent);
        System.out.println("companyId"+companyId);


        Call<List<AggregationResponce>> call = ApiClinet.getUserService().aggregationApi(aggregationRequest);
        call.enqueue(new Callback<List<AggregationResponce>>() {
            @Override
            public void onResponse(Call<List<AggregationResponce>> call, Response<List<AggregationResponce>> response) {
                List<AggregationResponce> aggregationResponces = response.body();

                if (response.isSuccessful()) {
                    message = aggregationResponces.get(0).getMessage();

                    System.out.println("message" + message);
                    System.out.println("SUCCESSFULLY API CALL");

                    if (message.equals("Aggregation successfull....")) {
                        getDispatchDetails();
                    }
                    else {
                        errorLabel.setText(message);
                        errorLabeleCv.setBackgroundResource(R.drawable.err_label_red_bg);
                        errorLabel.setTextColor(getResources().getColor(R.color.red));
                        scanNo.setText("");
                    }

                }
            }
            @Override
            public void onFailure(Call<List<AggregationResponce>> call, Throwable t) {
                // Handle the error.
            }
        });
    }

    public void TeartiryBarcodeWithoutLoos() {
        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();
        JsonObject jsonObject1 = new JsonObject();
        JsonObject jsonObject2= new JsonObject();
        JsonObject jsonObject3 = new JsonObject();
        JsonObject jsonObject4 = new JsonObject();
        JsonObject jsonObject5 = new JsonObject();
        JsonObject jsonObject6 = new JsonObject();
        JsonArray jsonObject7 = new JsonArray();
        JsonObject jsonObject8 = new JsonObject();

        jsonObject.addProperty("company_id", companyId);
        jsonObject1.addProperty("prod_id", proId);
        jsonObject2.addProperty("batchid", batchIdd);
        jsonObject3.addProperty("is_sscc", 1);
        jsonObject4.addProperty("Loose", 0);
        jsonObject5.addProperty("product_matrix_id",proMatrixId );
        jsonObject8.addProperty("product_matrix_id", proMatrixId1);
        jsonObject6.addProperty("parent", scanNos);
        JsonObject uidJsonObject = new JsonObject();
        JsonArray childJsonArray = new JsonArray();
        childJsonArray.add(strwithoutspace);
        uidJsonObject.add("child", childJsonArray);
        jsonObject7.add(uidJsonObject);
        JsonObject wrapperJsonObject1 = new JsonObject();
        wrapperJsonObject1.add("UID", jsonObject7);

        JsonArray jsonArray = new JsonArray();
        jsonArray.add(jsonObject);
        jsonArray.add(jsonObject1);
        jsonArray.add(jsonObject2);
        jsonArray.add(jsonObject3);
        jsonArray.add(jsonObject4);
        jsonArray.add(jsonObject5);
        jsonArray.add(jsonObject8);
        jsonArray.add(jsonObject6);
        jsonArray.add(wrapperJsonObject1);
//        JsonObject wrapperJsonObject = new JsonObject();
//        wrapperJsonObject.add("jsonContent", jsonArray);

        String jsonContent = gson.toJson(jsonArray);
        AggregationRequest aggregationRequest = new AggregationRequest();
        aggregationRequest.setJsonContent(jsonContent);
        System.out.println("bulk"+jsonContent);
        System.out.println("companyId"+companyId);

        Call<List<AggregationResponce>> call = ApiClinet.getUserService().aggregationApi(aggregationRequest);
        call.enqueue(new Callback<List<AggregationResponce>>() {
            @Override
            public void onResponse(Call<List<AggregationResponce>> call, Response<List<AggregationResponce>> response) {
                List<AggregationResponce> aggregationResponces = response.body();

                if (response.isSuccessful()) {
                    message = aggregationResponces.get(0).getMessage();

                    System.out.println("message" + message);
                    System.out.println("SUCCESSFULLY API CALL");

                    if (message.equals("Aggregation successfull....")) {
                        getDispatchDetails();
                    }
                    else {
                        errorLabel.setText(message);
                        errorLabeleCv.setBackgroundResource(R.drawable.err_label_red_bg);
                        errorLabel.setTextColor(getResources().getColor(R.color.red));
                        scanNo.setText("");
                    }

                }
            }
            @Override
            public void onFailure(Call<List<AggregationResponce>> call, Throwable t) {
                // Handle the error.
            }
        });
    }

    public void getDispatchDetails() {
        CountRequest countRequest = new CountRequest();
        countRequest.setProductMatrix(proMatrixId1);
        countRequest.setBatchNo(batchName);

        Call<List<CountCaseResponce>> call = ApiClinet.getUserService().getCount(countRequest);
        call.enqueue(new Callback<List<CountCaseResponce>>() {
            @Override
            public void onResponse(Call<List<CountCaseResponce>> call, Response<List<CountCaseResponce>> response) {
                if (response.isSuccessful()) {
                    errorLabel.setText(message);
                    errorLabeleCv.setBackgroundResource(R.drawable.err_label_green_bg);
                    errorLabel.setTextColor(getResources().getColor(R.color.colorGreen));
                    scanNo.setEnabled(false);
                    List<CountCaseResponce> countCaseResponce = response.body();
                        myCount = countCaseResponce.get(0).getCountParent();
                        System.out.println("mycount"+myCount);
                        System.out.println("message" + message);
                        System.out.println("SUCCESSFULLY API CALL");
                        Intent intent = new Intent(AggregationComplete.this, AggregationMain.class);
                        intent.putExtra("batchId",String.valueOf(batchIdd));
                        intent.putExtra("companyId",String.valueOf(companyId));
                        intent.putExtra("proId",String.valueOf(proId));
                        intent.putExtra("proMatrixId",proMatrixId);
                        intent.putExtra("proMatrixId1",proMatrixId1);
                        intent.putExtra("childCount",cCount);
                        intent.putExtra("sId",sId);
                        intent.putExtra("countp",countParent);
                        intent.putExtra("ct",myCount);
                        intent.putExtra("cPak",cPak);
                        intent.putExtra("pPak",pPak);
                        intent.putExtra("batchName",batchName);
                        intent.putExtra("gtin",gtin);
                        intent.putExtra("gtinn",gtinn);
                        intent.putExtra("productName",productName);
                        startActivity(intent);
                        finish();
                }
            }
            @Override
            public void onFailure(Call<List<CountCaseResponce>> call, Throwable t) {
                // Handle the error.
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onStart() {

        super.onStart();
    }
    @Override
    protected void onStop() {
        super.onStop();
    }
}
