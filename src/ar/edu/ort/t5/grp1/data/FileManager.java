package ar.edu.ort.t5.grp1.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;
import ar.edu.ort.t5.grp1.misgastosdiarios.Categoria;
import ar.edu.ort.t5.grp1.misgastosdiarios.Reporte;

public class FileManager {
	public static void importarCategorias(Context context) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(context.getAssets().open("categorias.txt")));

			// do reading, usually loop until end of file reading
			String mLine;
			CategoriaData cData = new CategoriaData(context);
			while ((mLine = reader.readLine()) != null) {
				String[] vec = mLine.split(";");
				if (vec.length == 2) {
					Categoria categoria = new Categoria(Integer.parseInt(vec[0]), vec[1]);
					cData.insert(categoria);
				}

			}
		} catch (IOException e) {
			// log the exception
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					Log.e(Context.STORAGE_SERVICE, e.getMessage());
				}
			}
		}
	}

	public static void exportarReporte(Context context , List<Reporte> lista) {
		File file = new File(context.getExternalFilesDir("data"), "reporte.txt");
		Toast toast = Toast.makeText(context, "Exportando a " + file.getPath(), 1000 );
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
        String nl = System.getProperty("line.separator");
		for( Reporte i : lista){
			FileOutputStream fos;
			try {
				fos = new FileOutputStream(file, true);
	
				FileWriter fWriter;
	
				try {					
					fWriter = new FileWriter(fos.getFD());
					fWriter.write(i.getCategoria().getDescripcion()+";"+Float.toString(i.getImporte())+";"+i.getPorcentaje()+nl);
					
					fWriter.close();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					fos.getFD().sync();
					fos.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				Log.e(Context.STORAGE_SERVICE, e.getMessage());
			}
		}
	}
}
