package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan
public class DemojsonvalidatorApplication implements CommandLineRunner {

	public static void main(String[] args) {
		System.out.println("  args:[" + args[0] + "], ["+args[1]+"]");
		SpringApplication.run(DemojsonvalidatorApplication.class, args);
	}

	@Override
	public void run(String... args) {

		// 指定のファイル URL のファイルをバイト列として読み込む
		byte[] fileContentBytes = null;
		try {
			fileContentBytes = Files.readAllBytes(Paths.get( args[0] ));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 読み込んだバイト列を UTF-8 でデコードして文字列にする
		String fileContentStr = new String(fileContentBytes, StandardCharsets.UTF_8);

		fileContentBytes = null;
		try {
			fileContentBytes = Files.readAllBytes(Paths.get( args[1] ));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// 読み込んだバイト列を UTF-8 でデコードして文字列にする
		String fileJsonStr = new String(fileContentBytes, StandardCharsets.UTF_8);
		
		
		JSONObject rawSchema = null;
		try {
			rawSchema = new JSONObject(new JSONTokener(fileContentStr));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Schema schema = SchemaLoader.load(rawSchema);
		try {
			schema.validate(new JSONObject(fileJsonStr));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // throws a ValidationException if this object is invalid
	}
}
