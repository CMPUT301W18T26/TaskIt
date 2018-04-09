package com.tianditu.sdkDemo;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.tianditu.android.maps.GeoPoint;
import com.tianditu.android.maps.MapView;
import com.tianditu.android.maps.OnGetPoiResultListener;
import com.tianditu.android.maps.PoiInfo;
import com.tianditu.android.maps.PoiSearch;
import com.tianditu.android.maps.PoiSearch.CityInfo;
import com.tianditu.android.maps.PoiSearch.ProvinceInfo;
import com.tianditu.android.maps.TErrorCode;
import com.tianditu.android.maps.overlayutil.PoisOverlay;

/**
 030
 * 代码示例-关键字搜索
 031
 */

public class PoiSearchDemo extends Activity implements OnGetPoiResultListener {

    private static final int DIALOG_TYPE_LIST = 1;

    private static final int DIALOG_POI_LIST = 2;
    private static final int DIALOG_CITY_LIST = 3;
    private final static String TYPE_NAME[] = { "普通搜索", "视野内搜索", "周边搜索", "在北京搜索" };
    private MapView mMapView;
    private Button mBtnType;
    private EditText mEditKey;
    private Button mBtnSearch;
    private Button mBtnList;
    private Button mBtnPre;
    private Button mBtnNext;



    private TextView mTextTips;



        // 搜索条件

    private String mKeyword;

    private int mSearchType = 0;

        // 搜索类

    private PoiSearch mPoiSearch;

        // 搜索结果

    private ArrayList<PoiInfo> mPoiList;

    private ArrayList<ProvinceInfo> mCityList;

        // 覆盖物

    private PoisOverlay mPoisOverlay;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.poisearchdemo);



        mMapView = (MapView) findViewById(R.id.mapview);

        mMapView.setLogoPos(MapView.LOGO_RIGHT_TOP);

        mMapView.setBuiltInZoomControls(true);



        // type

        mBtnType = (Button) findViewById(R.id.buttonType);

        OnClickListener listener = new OnClickListener() {

            @Override

            public void onClick(View v) {

                removeDialog(DIALOG_TYPE_LIST);

                showDialog(DIALOG_TYPE_LIST);

            }



        };

        mBtnType.setOnClickListener(listener);

        // edit

        mEditKey = (EditText) findViewById(R.id.editKey);

        mEditKey.setText("餐饮");

        // search

        mBtnSearch = (Button) findViewById(R.id.buttonSearch);

        listener = new OnClickListener() {

            @Override

            public void onClick(View v) {

                String key = mEditKey.getText().toString();

                searchKey(key, mSearchType);

            }


        };

        mBtnSearch.setOnClickListener(listener);

        // list

        mBtnList = (Button) findViewById(R.id.buttonList);

        listener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPoiList != null && mPoiList.size() != 0) {
                    removeDialog(DIALOG_POI_LIST);
                    showDialog(DIALOG_POI_LIST);
                } else if (mCityList != null && mCityList.size() != 0) {
                    removeDialog(DIALOG_CITY_LIST);
                    showDialog(DIALOG_CITY_LIST);
                }

            }


        };

        mBtnList.setOnClickListener(listener);

        // pre

        mBtnPre = (Button) findViewById(R.id.buttonPre);

        listener = new OnClickListener() {

            @Override

            public void onClick(View v) {

            }



        };

        mBtnPre.setOnClickListener(listener);


        // next

        mBtnNext = (Button) findViewById(R.id.buttonNext);

        listener = new OnClickListener() {

            @Override

            public void onClick(View v) {

                if (mPoiList != null) {

                    removeDialog(DIALOG_POI_LIST);

                    showDialog(DIALOG_POI_LIST);

                } else if (mCityList != null) {

                    removeDialog(DIALOG_CITY_LIST);

                    showDialog(DIALOG_CITY_LIST);



                }

            }

        };

        mBtnNext.setOnClickListener(listener);



        // tips

        mTextTips = (TextView) findViewById(R.id.textViewTips);

        mTextTips.setText("");

        mSearchType = 0;

        mBtnType.setText(TYPE_NAME[mSearchType]);

        mBtnList.setEnabled(false);

        mBtnPre.setEnabled(false);

        mBtnNext.setEnabled(false);

    }

    private void searchKey(String key, int type) {

        mKeyword = key;

        mTextTips.setText("Start Search key = " + key + ", type = " + type);

        mBtnList.setEnabled(false);

        mBtnPre.setEnabled(false);

        mBtnNext.setEnabled(false);



        if (mPoisOverlay != null) {

            mMapView.removeOverlay(mPoisOverlay);

            mPoisOverlay = null;

        }



        switch (type) {

            case 0: // 普通

                mPoiSearch = new PoiSearch(this, this, mMapView);

                mPoiSearch.search(key, null, null);

                break;

            case 1: // 视图范围内

                GeoPoint p1 = mMapView.getProjection().fromPixels(0, 0);

                GeoPoint p2 = mMapView.getProjection().fromPixels(mMapView.getWidth(),

                        mMapView.getHeight());

                mPoiSearch = new PoiSearch(this, this, mMapView);

                mPoiSearch.search(key, p1, p2);

                break;

            case 2: // 周边

                mPoiSearch = new PoiSearch(this, this, mMapView);

                GeoPoint center = mMapView.getMapCenter();

                mPoiSearch.search(key, center, 20000);

                break;

            case 3: // 行政区

                mPoiSearch = new PoiSearch(this, this, mMapView);

                mPoiSearch.search("156110000", key);

                break;

        }



    }



    @Override

    public void OnGetPoiResult(ArrayList<PoiInfo> poiInfo, ArrayList<ProvinceInfo> cityInfo,

                               String keyword, int error) {

        mPoiList = poiInfo;

        mCityList = cityInfo;



        int poiSize = poiInfo != null ? poiInfo.size() : 0;

        int citySize = cityInfo != null ? cityInfo.size() : 0;



        if (error != TErrorCode.OK) {

            mTextTips.setText("Failure to search the keywords key = " + mKeyword + ", type = " + mSearchType

                            + ", error = " + error);

            return;

        }

        if (poiSize == 0 && citySize == 0) {

            mTextTips.setText("No Result key = " + mKeyword + ", type = " + mSearchType);

            return;

        }



        String tips = String.format(Locale.getDefault(),

                "Show the result of searching keywords key =%s, type = %s, poiSize = %s, citySize = %s", mKeyword,

                TYPE_NAME[mSearchType], poiSize, citySize);

        mTextTips.setText(tips);

        mBtnList.setEnabled(true);
        if (poiSize > 0) {
            mBtnPre.setEnabled(true);
            mBtnNext.setEnabled(true);

        }


        // 添加覆盖物

        Drawable marker = getResources().getDrawable(R.drawable.poi_xml);

        mPoisOverlay = new PoisOverlay(marker);

        mPoisOverlay.setData(poiInfo);

        mPoisOverlay.setDrawFocusedItem(true);

        mPoisOverlay.showFocusAlways(true);

        mPoisOverlay.populate();

        mMapView.addOverlay(mPoisOverlay);



        GeoPoint point = poiInfo.get(0).mPoint;

        mMapView.getController().animateTo(point);

    }

    @Override

    protected Dialog onCreateDialog(int id) {

        switch (id) {

            case DIALOG_TYPE_LIST:

                return showTypeList();

            case DIALOG_POI_LIST:

                return showPoiList();

            case DIALOG_CITY_LIST:

                return showCityList();

            default:

                break;

        }

        return null;

    }



    private Dialog showTypeList() {

        DialogInterface.OnClickListener listenerList = new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                mSearchType = which;

            }

        };



        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                mBtnType.setText(TYPE_NAME[mSearchType]);

            }

        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("select the method of searching");

        builder.setSingleChoiceItems(TYPE_NAME, mSearchType, listenerList);

        builder.setPositiveButton("Confirm", listener);

        return builder.create();

    }



    private Dialog showPoiList() {

        int size = mPoiList.size();

        String items[] = new String[size];

        for (int i = 0; i < size; ++i) {

            items[i] = "name :" + mPoiList.get(i).mStrName + "\n";

            items[i] += "addr :" + mPoiList.get(i).mStrAdd + "\n";

            items[i] += "tel :" + mPoiList.get(i).mStrTel;

        }

        DialogInterface.OnClickListener listenerList = new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                GeoPoint geoPoint = mPoisOverlay.getItem(which).getPoint();

                mPoisOverlay.setFocusID(which);

                mMapView.getController().setCenter(geoPoint);



                dialog.dismiss();

            }

        };



        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("POI结果");

        builder.setItems(items, listenerList);

        return builder.create();

    }



    String mDlgCityTitle = "";

    List<CityInfo> mDlgCityList = null;



    private Dialog showCityList() {

        int size = mDlgCityList != null ? mDlgCityList.size() : 0;

        String items[] = new String[size];

        for (int i = 0; i < size; ++i) {

            items[i] = mDlgCityList.get(i).mStrName;

        }

        DialogInterface.OnClickListener listenerList = new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

            }

        };



        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(mDlgCityTitle);

        builder.setItems(items, listenerList);

        return builder.create();



    }



}
