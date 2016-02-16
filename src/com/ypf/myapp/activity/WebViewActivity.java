package com.ypf.myapp.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.webkit.WebView;
import com.ypf.myapp.R;
import com.ypf.myapp.bean.Contact;
import com.ypf.myapp.utils.IntentsUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ypf on 2016/2/16.
 */
public class WebViewActivity extends Activity {
    private WebView webView;
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        context = this;

        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new ContactsPlugin(), "contactsAction");
        webView.loadUrl("file:///android_asset/index.html");
    }

    public List<Contact> getContactList() {
        List<Contact> contacts = new ArrayList<Contact>();

        contacts.add(new Contact(1, "wjh", "1111"));
        contacts.add(new Contact(2, "jhw", "1331"));
        contacts.add(new Contact(3, "hwj", "1141"));
        contacts.add(new Contact(4, "hhw", "1511"));
        contacts.add(new Contact(5, "hhj", "1116"));
        contacts.add(new Contact(6, "jww", "1121"));

        return contacts;
    }

    /**
     * 这个类提供出了一个视图和业务层通信的接口。HTML 中，通过这个类的实例，间接与业务 Bean 通信。
     * 为什么不直接将业务类提供给  webView, 让  HTML 中直接访问到这个类。而多出这样 "插件"~~
     * 我想：目前的这样一种架构，Activity 甚至有些类似于控制器的概念了。有点像 struts 中的 Action。
     * 在使用了 struts 框架的项目架构中，Action 也是被划分到视图层的。它和JSP页面共同完成准备数据和页面跳转的工作。
     * 因此，这里我们也不应该让 HTML 中的 JS 直接与业务层耦合。实现表现层和业务层的解耦
     */
    private class ContactsPlugin {
        /**
         * 此方法将执行 JS 代码，调用 JS 函数：show()
         * 实现，将联系人信息展示到 HTML 页面上
         */
        @SuppressWarnings("unused")
        public void getContacts() {
            List<Contact> contacts = getContactList();
            try {
                JSONArray array  = new JSONArray();
                for(Contact contact : contacts) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", contact.getId());
                    jsonObject.put("mobile", contact.getMobile());
                    jsonObject.put("name", contact.getName());
                    array.put(jsonObject);
                }
                String json = array.toString();
                webView.loadUrl("javascript:show('"+ json +"')");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        /**
         * 拨号
         */
        @SuppressWarnings("unused")
        public void call(String phoneCode) {
            IntentsUtil.skipTelPhone(context, phoneCode);
        }
    }
}