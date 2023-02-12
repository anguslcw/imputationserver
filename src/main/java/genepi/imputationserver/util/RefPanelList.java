package genepi.imputationserver.util;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;

public class RefPanelList {

	public static String FILENAME = "panels.txt";

	private List<RefPanel> panels;

	public RefPanelList() {
		panels = new Vector<RefPanel>();
	}

	public List<RefPanel> getPanels() {
		return panels;
	}

	public void setPanels(List<RefPanel> panels) {
		this.panels = panels;
	}

	public RefPanel getById(String id, Object properties) throws IOException {

		if (properties != null) {
			RefPanel panel = new RefPanel();
			Map<String, Object> map = (Map<String, Object>) properties;

			if (map.get("hdfs") != null) {
				panel.setHdfs(map.get("hdfs").toString());
			} else {
				throw new IOException("Property 'hdfs' not found in cloudgene.yaml.");
			}

			if (map.get("id") != null) {
				panel.setId(map.get("id").toString());
			} else {
				throw new IOException("Property 'id' not found in cloudgene.yaml.");
			}

			if (map.get("legend") != null) {
				panel.setLegend(map.get("legend").toString());
			} else {
				throw new IOException("Property 'legend' not found in cloudgene.yaml.");
			}

			if (map.get("mapEagle") != null) {
				panel.setMapEagle(map.get("mapEagle").toString());
			}

			if (map.get("refEagle") != null) {
				panel.setRefEagle(map.get("refEagle").toString());
			}
			
			if (map.get("mapBeagle") != null) {
				panel.setMapBeagle(map.get("mapBeagle").toString());
			}

			if (map.get("refBeagle") != null) {
				panel.setRefBeagle(map.get("refBeagle").toString());
			}

			if (map.get("populations") != null) {
				panel.setPopulations((Map<String, String>) map.get("populations"));
			} else {
				throw new IOException("Property 'populations' not found in cloudgene.yaml.");
			}

			if (map.get("samples") != null) {
				panel.setSamples((Map<String, String>) map.get("samples"));
				;
			} else {
				throw new IOException("Property 'samples' not found in cloudgene.yaml.");
			}

			if (map.get("qcFilter") != null) {
				panel.setQcFilter((Map<String, String>) map.get("qcFilter"));
			}

			// optional parameters
			if (map.get("build") != null) {
				panel.setBuild(map.get("build").toString());
			} else {
				panel.setBuild(null);
			}

			if (map.get("range") != null) {
				panel.setRange(map.get("range").toString());
			} else {
				panel.setRange(null);
			}

			if (map.get("mapMinimac") != null) {
				panel.setMapMinimac(map.get("mapMinimac").toString());
			} else {
				panel.setMapMinimac(null);
			}

			return panel;
		}
		for (RefPanel panel : panels) {
			if (panel.getId().equals(id)) {
				return panel;
			}
		}
		return null;
	}

	public void add(RefPanel panel) {
		panels.add(panel);
	}

	public static RefPanelList loadFromFile(String filename) {

		if (new File(filename).exists()) {
			try {
				YamlReader reader = new YamlReader(new FileReader(filename));
				reader.getConfig().setPropertyElementType(RefPanelList.class, "panels", RefPanel.class);
				reader.getConfig().setClassTag("genepi.minicloudmac.hadoop.util.RefPanelList", RefPanelList.class);
				RefPanelList result = reader.read(RefPanelList.class);
				return result;
			} catch (Exception e) {
				return new RefPanelList();
			}
		} else {
			return new RefPanelList();
		}

	}

	public static void saveToFile(String filename, RefPanelList panels) throws IOException {

		YamlWriter writer = new YamlWriter(new FileWriter(filename));
		writer.getConfig().setClassTag("genepi.minicloudmac.hadoop.util.RefPanelList", RefPanelList.class);
		writer.getConfig().setPropertyElementType(RefPanelList.class, "panels", RefPanel.class);
		writer.write(panels);
		writer.close();

	}

}
