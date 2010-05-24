package cn.com.crm;


import java.io.StringWriter;
import java.io.Writer;
import java.util.Locale;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.tools.generic.NumberTool;

public class NumberToolExample {

  public static void main(String[] args) throws Exception {
    Velocity.init();

    Template t = Velocity.getTemplate("./src/numberTool.vm");

    VelocityContext ctx = new VelocityContext();
    ctx.put("number", new NumberTool());
    ctx.put("aNumber", new Double(0.95111));
//    ctx.put("aLocale", Locale.CHINESE);

    Writer writer = new StringWriter();
    t.merge(ctx, writer);

    System.out.println(writer);
  }
}