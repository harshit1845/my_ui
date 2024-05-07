//package com.example.makesurest.aggregration;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.io.File;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.io.IOException;
//
//public class UIDDetails {
//    private String GTIN;
//    private String BatchNo;
//    private String UID;
//    private String ExpDate;
//    private String MfgDate;
//    private String BarcodeData;
//    private String FailReason;
//
//    public UIDDetails(String _UID, String _GTIN, String _Exp, String _Batch) {
//        GTIN = _GTIN;
//        BatchNo = _Batch;
//        UID = _UID;
//        ExpDate = _Exp;
//        BarcodeData = "01" + _GTIN + "17" + ExpDate + "10" + BatchNo + ((char) 29) + "21" + UID;
//    }
//
//    public UIDDetails(String _BarcodeData, String RefBatch) {
//        try {
//            if (_BarcodeData.startsWith("\u001d")) {
//                _BarcodeData = _BarcodeData.substring(1);
//            }
//            _BarcodeData = _BarcodeData.replace("<GS>", String.valueOf((char) 29));
//            if (_BarcodeData.charAt(0) == (char) 29) {
//                _BarcodeData = _BarcodeData.substring(1);
//            }
//            BarcodeData = _BarcodeData;
//
//            String[] formats = new String[4];
//                formats[0] = "i:01;v:gtin:14;i:17;v:exp:6;i:10;v:batch:.20;f:29;i:21;v:uid:.20;f:29";
//                formats[1] = "i:01;v:gtin:14;i:10;v:batch:.20;f:29;i:11;v:mfg:6;i:17;v:exp:6;i:21;v:uid:.20;f:29";
//                formats[2] = "i:01;v:gtin:13;i:17;v:exp:6;i:10;v:batch:.20;f:29;i:21;v:uid:.20;f:29";
//                formats[3] = "i:00;v:uid:18;i:02;v:gtin:14;i:17;v:exp:6;i:11;v:mfg:6;i:37;v:childcount:.8;f:29;i:10;v:batch:.20;f:29;i:241;v:customerpartno:.30;f:29;i:400;v:purchaseorderno:.30;f:29";
//
//            DecodeResult[] results = null;
//            boolean bfound = false;
//            for (String Format : formats) {
//                if (!Format.trim().equals("")) {
//                    results = Decode(BarcodeData, Format, RefBatch);
//                    bfound = true;
//                    for (DecodeResult result : results) {
//                        if (!result.getFailReason().equals("")) {
//                            FailReason = result.getFailReason();
//                            bfound = false;
//                            break;
//                        }
//                        if (!result.isResult() && !result.getType().equals("f")) {
//                            bfound = false;
//                        }
//                    }
//                    if (FailReason.equals("Function character not found")) {
//                        break;
//                    }
//                    if (bfound) {
//                        break;
//                    }
//                }
//            }
//            if (bfound) {
//                for (DecodeResult result : results) {
//                    if (result.getValue().equals("gtin")) {
//                        GTIN = result.getFoundValue();
//                    } else if (result.getValue().equals("exp")) {
//                        ExpDate = result.getFoundValue();
//                    } else if (result.getValue().equals("mfg")) {
//                        MfgDate = result.getFoundValue();
//                    } else if (result.getValue().equals("batch")) {
//                        BatchNo = result.getFoundValue();
//                    } else if (result.getValue().equals("uid")) {
//                        UID = result.getFoundValue();
//                    }
//                }
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    private DecodeResult[] Decode(String data, String format, String RefBatch) {
//        List<DecodeResult> lstDecoded = new ArrayList<>();
//        int icurr = 0;
//        String[] Variables = format.split(";");
//        int refBatchLength = RefBatch != "" ? RefBatch.length() : 1;
//        for (int i = 0; i <= Variables.length - 1; i++) {
//            String item = Variables[i];
//            if (icurr >= data.length()) {
//                break;
//            }
//            String[] attributes = item.split(":");
//            if (attributes[0].equals("i")) {
//                String FoundValue = data.substring(icurr, icurr + Integer.parseInt(attributes[1]));
//                if (attributes[1].equals(FoundValue)) {
//                    lstDecoded.add(new DecodeResult(attributes[0], attributes[1], FoundValue, true, ""));
//                } else {
//                    lstDecoded.add(new DecodeResult(attributes[0], attributes[1], FoundValue, false, "Identifier miss match"));
//                }
//                icurr += Integer.parseInt(attributes[1]);
//            } else if (attributes[0].equals("f")) {
//                String FoundValue = data.substring(icurr, icurr + 1);
//                if (FoundValue.equals(String.valueOf((char) Integer.parseInt(attributes[1])))) {
//                    lstDecoded.add(new DecodeResult(attributes[0], attributes[1], attributes[1], true, ""));
//                    icurr += 1;
//                } else {
//                    int iasc = (int) (FoundValue.charAt(0));
//                    lstDecoded.add(new DecodeResult(attributes[0], attributes[1], String.valueOf(iasc), false, "Function character not found"));
//                }
//            } else if (attributes[0].equals("v")) {
//                if (attributes[2].contains(".")) {
//                    String SubData = "";
//                    if (Variables.length > i + 1 && Variables[i + 1].split(":")[0].equals("f") && data.substring(icurr).contains(String.valueOf((char) Integer.parseInt(Variables[i + 1].split(":")[1])))) {
//                        SubData = data.substring(icurr).split(String.valueOf((char) Integer.parseInt(Variables[i + 1].split(":")[1])))[0];
//                    } else if (Variables.length > i + 1 && Variables[i + 1].split(":")[0].equals("i") && data.substring(icurr).contains(Variables[i + 1].split(":")[1])) {
//                        SubData = SplitWith(data.substring(icurr), Variables[i + 1].split(":")[1], (Variables[i].split(":")[1].equals("batch") ? refBatchLength : 1))[0];
//                        if ((icurr + SubData.length() < data.length())) {
//                            boolean tmpResult = CheckFerther(i + 1, Variables, data.substring(icurr + SubData.length()));
//                            int iinc = 0;
//                            if (!tmpResult) {
//                                while (!tmpResult && (icurr + SubData.length() + iinc + 1) < data.length()) {
//                                    iinc += 1;
//                                    tmpResult = CheckFerther(i + 1, Variables, data.substring(icurr + SubData.length() + iinc));
//                                }
//                            }
//                            if (tmpResult) {
//                                SubData = data.substring(icurr, icurr + SubData.length() + iinc);
//                            }
//                        }
//                    } else if (Variables.length > i + 2 && Variables[i + 2].split(":")[0].equals("i") && data.substring(icurr).contains(Variables[i + 2].split(":")[1])) {
//                        SubData = SplitWith(data.substring(icurr), Variables[i + 2].split(":")[1], (Variables[i].split(":")[1].equals("batch") ? refBatchLength : 1))[0];
//                        if ((icurr + SubData.length() < data.length())) {
//                            boolean tmpResult = CheckFerther(i + 2, Variables, data.substring(icurr + SubData.length()));
//                            int iinc = 0;
//                            if (!tmpResult) {
//                                while (!tmpResult && (icurr + SubData.length() + iinc + 1) < data.length()) {
//                                    iinc += 1;
//                                    tmpResult = CheckFerther(i + 2, Variables, data.substring(icurr + SubData.length() + iinc));
//                                }
//                            }
//                            if (tmpResult) {
//                                SubData = data.substring(icurr, icurr + SubData.length() + iinc);
//                            }
//                        }
//                    } else {
//                        SubData = data.substring(icurr);
//                    }
//                    int LengthLimit = Integer.parseInt(attributes[2].replace(".", ""));
//                    if (SubData.length() <= LengthLimit) {
//                        lstDecoded.add(new DecodeResult(attributes[0], attributes[1], SubData, true, ""));
//                    } else {
//                        lstDecoded.add(new DecodeResult(attributes[0], attributes[1], SubData, false, "Length of " + attributes[1] + " must be up to " + LengthLimit));
//                    }
//                    icurr += SubData.length();
//                } else {
//                    lstDecoded.add(new DecodeResult(attributes[0], attributes[1], data.substring(icurr, icurr + Integer.parseInt(attributes[2])), true, ""));
//                    icurr += Integer.parseInt(attributes[2]);
//                }
//            }
//        }
//        return lstDecoded.toArray(new DecodeResult[0]);
//    }
//
//    private boolean CheckFerther(int StartIndex, String[] Variables, String RestString) {
//        List<String> rest = new ArrayList<>();
//        for (int index = StartIndex; index <= Variables.length - 1; index++) {
//            rest.add(Variables[index]);
//        }
//        DecodeResult[] tempresult = Decode(RestString, String.join(";", rest.toArray()), "");
//        boolean bPass = true;
//        for (DecodeResult result : tempresult) {
//            if (!result.getType().equals("f") && !result.isResult()) {
//                bPass = false;
//                break;
//            }
//        }
//        return bPass;
//    }
//
//    private String[] SplitWith(String Data, String SplitString, int RefLength) {
//        String[] SData = new String[2];
//        SData[0] = Data.substring(0, RefLength);
//        SData[1] = Data.substring(RefLength);
//        String tryBatch = SData[0];
//        String tryuid = SData[1];
//        boolean tryDone = true;
//        while (!tryuid.substring(0, SplitString.length()).equals(SplitString)) {
//            if (tryuid.length() > SplitString.length()) {
//                tryBatch += (Character.isLetterOrDigit(tryuid.charAt(0))) ? tryuid.charAt(0) : "";
//                tryuid = tryuid.substring(1);
//            } else {
//                tryDone = false;
//                break;
//            }
//        }
//        if (!tryDone) {
//            tryBatch = SData[0];
//            tryuid = SData[1];
//            tryDone = true;
//            while (!tryuid.substring(0, SplitString.length()).equals(SplitString)) {
//                if (tryBatch.length() > SplitString.length()) {
//                    tryuid = tryBatch.charAt(tryBatch.length() - 1) + tryuid;
//                    tryBatch = tryBatch.substring(0, tryBatch.length() - 1);
//                } else {
//                    tryDone = false;
//                    break;
//                }
//            }
//        }
//        if (tryDone) {
//            SData[0] = tryBatch;
//            SData[1] = tryuid.substring(2);
//        }
//        return SData;
//    }
//}