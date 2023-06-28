package edu.swu.cs.utils;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.fill.Column;

import java.util.Collections;

public class FastAutoGeneratorTest {
    public static void main(String[] args) {
        String parentPackage = "edu.swu.cs";
        String mapperpath = "edu/swu/cs";
        FastAutoGenerator.create("jdbc:mysql://127.0.0.1:3306/hospital?useUnicode=true&useSSL=false&characterEncoding=utf8", "root", "root")
                .globalConfig(builder -> {
                    builder.author("liujian") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(System.getProperty("user.dir") + "/Service/service-product"+"/src/main/java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent(parentPackage) // 设置父包名
                            //.moduleName("system") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, System.getProperty("user.dir") + "/Service/service-product"+"/src/main/java/" + mapperpath + "/mapper/xml")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("doctor_leave")// 设置需要生成的表名
                           // .addTablePrefix("sys_") // 设置过滤表前缀
                            .controllerBuilder().enableRestStyle().enableHyphenStyle()
                            .entityBuilder().enableLombok();
//                            .addTableFills(
//                                    new Column("create_time", FieldFill.INSERT)
//                            ).addTableFills(
//                                    new Column("update_time", FieldFill.INSERT_UPDATE)
//                            );

                })
                //.templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
