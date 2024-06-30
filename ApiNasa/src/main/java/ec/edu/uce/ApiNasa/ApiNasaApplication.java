package ec.edu.uce.ApiNasa;

import ec.edu.uce.ApiNasa.models.Api;
import ec.edu.uce.ApiNasa.view.Window;

import javax.swing.*;
import java.awt.*;


public class ApiNasaApplication {
/*
author: Cruz Kevin
tema: uso de la api de la NASA
*/
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				Window frame = new Window();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

	}
}