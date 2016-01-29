/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import static java.lang.System.in;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Kyran
 */
public class WrapperHelper {

    public WrapperHelper() {
    }

    public void trainSystem(InputStream inputStream) {
        List<SiteFeatures> siteList = getDataFromFile(inputStream);
        List<Wrapper> wrapperList = new ArrayList<Wrapper>();
        for (int i = 0; i < siteList.size(); i++) {
            wrapperList.add(generateWrapper(siteList.get(i)));
        }
        Wrapper wrapper = aggregateWrappers(wrapperList);
        //save into db at end
    }

    private Wrapper generateWrapper(SiteFeatures feature) {
        Wrapper wrapper = null;

        try {
            Document doc = Jsoup.connect(feature.getUrl()).get();
            Elements elements = doc.getAllElements();
            Iterator it = feature.getFeatureMap().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                System.out.println(pair.getKey() + " = " + pair.getValue());
                it.remove(); // avoids a ConcurrentModificationException
                if (pair.getValue().toString() != "") {
                    Elements searchElements = elements.select(String.format("*:contains(>%s<)", pair.getValue().toString()));
                    for (int i = 0; i < searchElements.size(); i++) {
                        //want to put all elements in to the next stage if possible as aggregation stage can weed out the ones that wont work
                        Element element = searchElements.get(i);
                    }
                    System.out.println("");
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(WrapperHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

        return wrapper;
    }

    private Wrapper aggregateWrappers(List<Wrapper> wrapperList) {
        return null;
    }

    public void testSystem() {

    }

    public List<SiteFeatures> getDataFromFile(InputStream inputStream) {
        List<SiteFeatures> siteList = new ArrayList<SiteFeatures>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = br.readLine()) != null) {
                siteList.add(new SiteFeatures(line));
            }
        } catch (Exception e) {

        }
        return siteList;
    }

}
