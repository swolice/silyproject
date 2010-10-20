package util;

import java.util.HashMap;
import java.io.StringWriter;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.VelocityContext;
import java.util.Iterator;
import java.util.Map.Entry;
import org.json.JSONObject;
import org.json.*;
//import org.apache.velocity.tools.generic.DateTool;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;

public class VelocityUtil {

    static {
        try {
            Velocity.init();
//            Velocity.setApplicationAttribute("dateTool",new DateTool());
        } catch (Exception e) {
            Logger.getLogger().error("Problem initializing Velocity : " + e);
        }
    }

    public VelocityUtil() {
    }

    public static String evaluate(String template, HashMap data) throws Exception {
        if (template == null)
            return null;
        if (data == null)
            data = new HashMap();

        StringWriter w = new StringWriter();

        VelocityContext context = new VelocityContext();
//        context.put("dateTool", new DateTool());

        for (Object o : data.entrySet()) {
            Entry entry = (Entry) o;
            context.put(entry.getKey().toString(), entry.getValue());
        }
        try {
            Velocity.evaluate(context, w, "VelocityUtil", template);
        } catch (Exception e) {
            Logger.getLogger().error("´´½¨Ä£°å´íÎó£º");
            Logger.getLogger().error("template £º "+template);
            Logger.getLogger().error("data     £º "+data.entrySet());

            throw e;
        }

        return w.toString();
    }


    public static Object getObject(Object obj){
        if (obj instanceof JSONArray) {
            JSONArray arr = (JSONArray) obj;
            List list = new ArrayList();
            for (int i = 0; i < arr.length(); i++) {
                try {
                    list.add(getObject(arr.get(i)));
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
            return list;
        } else {
            return obj;
        }
    }


    public static String evaluate(String template, JSONObject json) throws Exception {
        if (template == null)
            return null;
        if (json == null)
            return evaluate(template, new HashMap());

        Iterator keys = json.keys();
        HashMap data = new HashMap();
        while (keys.hasNext()) {
            try {
                String key = keys.next().toString();
                Object obj = json.get(key);

                data.put(key, getObject(obj));
            } catch (JSONException ex) {
                ex.printStackTrace();
            }

        }
        return evaluate(template, data);

    }

    public static String readFile(InputStream is) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String temp;
            StringBuffer buf = new StringBuffer();
            while ((temp = br.readLine()) != null) {
                buf.append(temp);
            }
            return buf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


}
