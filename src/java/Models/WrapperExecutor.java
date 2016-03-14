/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Models.Enums.FeatureEnum;
import Models.Structures.SiteFeatures;
import Models.Structures.Rule;
import Models.Structures.TicketListFeatures;
import Models.Structures.Wrapper;
import SQL.WrapperDB;
import SQL.WrapperlessDomainDB;
import com.sun.org.apache.xerces.internal.xs.StringList;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Kyran
 */
public class WrapperExecutor {

    public final static String MODIFIER_STRING = "@#@"; //unique string combinatioin that signifies that ignore the next bit of the string
    public final static String MODIFIER_REGEX = "@#@";

    WrapperDB wrapperDB;
    WrapperlessDomainDB wrapperlessDB;
    WebPageManager wpManager;

    SearchResultMiner srm;

    List<Wrapper> listWrappers;
    List<Wrapper> individualWrappers;

    public WrapperExecutor(Connection conn) {
        wrapperDB = new WrapperDB(conn);
        wrapperlessDB = new WrapperlessDomainDB(conn);
        srm = new SearchResultMiner();
        wpManager = new WebPageManager();
    }

    public WrapperExecutor() {
        //for when is used by wrapper tester
    }

    public List<SiteFeatures> performSearch(String searchString) {
        List<String> urlList = srm.getSearchResults(searchString);

        Map<String, ArrayList<String>> domainList = getDomainListFromUrlList(urlList);

        domainList = removeDomainsWithoutWrappers(domainList); //this also sets wrapper lists

        List<SiteFeatures> searchResults = new ArrayList<>();

        searchResults = getSearchResults(domainList);

        return searchResults;
    }

    public Map<String, ArrayList<String>> removeDomainsWithoutWrappers(Map<String, ArrayList<String>> originalMap) {
        listWrappers = new ArrayList<>();
        individualWrappers = new ArrayList<>();

        Object[] domains = originalMap.keySet().toArray();
        List<Wrapper> wrapperList;

        List<String> wrapperlessDomains = new ArrayList<String>();

        for (int i = 0; i < domains.length; i++) {
            String domain = domains[i].toString().trim();
            wrapperList = wrapperDB.getWrappers(domain);

            if (wrapperList.size() > 0) {
                for (int j = 0; j < wrapperList.size(); j++) {
                    if (wrapperList.get(j).getType() == 0) {
                        individualWrappers.add(wrapperList.get(j));
                    } else {
                        listWrappers.add(wrapperList.get(j));
                    }
                }
            } else {
                wrapperlessDomains.add(domain);
                originalMap.remove(domain);
            }
        }

        if (wrapperlessDomains.size() > 0) {
            wrapperlessDB.addDomains(wrapperlessDomains);
        }

        return originalMap;
    }

    // <editor-fold desc="Getting domains from url ">
    public Map<String, ArrayList<String>> getDomainListFromUrlList(List<String> urlList) {
        Map<String, ArrayList<String>> domainList = new HashMap<String, ArrayList<String>>();

        for (int i = 0; i < urlList.size(); i++) {
            String domain = getDomainFromURL(urlList.get(i));
            if (!domain.equals("")) {
                if (domainList.containsKey(domain)) {
                    ArrayList<String> al = domainList.get(domain);
                    al.add(urlList.get(i));
                    domainList.put(domain, al);
                } else {
                    ArrayList<String> al = new ArrayList<String>();
                    al.add(urlList.get(i));
                    domainList.put(domain, al);
                }

            }
        }

        return domainList;
    }

    public String getDomainFromURL(String url) {
        url = url.toLowerCase();
        String hostName = url;
        if (!url.equals("")) {
            if (url.startsWith("http") || url.startsWith("https")) {
                try {
                    URL netUrl = new URL(url);
                    String host = netUrl.getHost();
                    if (host.startsWith("www")) {
                        hostName = host.substring("www".length() + 1);
                    } else {
                        hostName = host;
                    }
                } catch (MalformedURLException e) {
                    hostName = url;
                }
            } else if (url.startsWith("www")) {
                hostName = url.substring("www".length() + 1);
            }
            return hostName;
        } else {
            return "";
        }
    }
    // </editor-fold>

    // <editor-fold desc="Extracting data from wrapper ">
    public String getValFromRule(String htmlFile, Rule rule, boolean testHT, boolean testHeadOnly) {
        //use this when testing and executing but run the head and tail through first so only passing substring of the html
        int indexStart = -1;
        int indexEnd = -1;

        String html = testHT ? extractOC(htmlFile, rule, testHeadOnly) : htmlFile;

        if (!rule.getLeft().contains(MODIFIER_STRING)) {
            indexStart = html.indexOf(rule.getLeft()) + rule.getLeft().length() + 1;
        } else {
            String[] split = rule.getLeft().split(MODIFIER_REGEX);
            int pointer = 0;
            while (true) {
                pointer = html.indexOf(split[0], pointer);
                boolean location = true;
                int innerPointer = pointer;
                //could do differently to use the max length of an unknown entity eg 30 from method where set the |#| IMPORTANT IF DOESNT WORK
                for (int j = 1; j < split.length; j++) {
                    innerPointer = html.indexOf(split[j], innerPointer);
                    if (innerPointer == -1) {
                        location = false;
                        break;
                    }
                }
                if (location) {
                    //have found it and can set the end index
                    indexStart = innerPointer + split[split.length - 1].length() + 1;
                    break;
                } else {
                    break;
                }
            }
        }

        if (indexStart == -1) {
            return "";
        }
        //doesnt need to be nested as is repeated in both
        if (!rule.getRight().contains(MODIFIER_STRING)) {
            indexEnd = html.indexOf(rule.getRight(), indexStart);
        } else {
            String[] split = rule.getRight().split(MODIFIER_REGEX);
            int pointer = indexStart; //start here as know it cant be before and dont know the length of the item you are searching for
            while (true) { //should only go through once on this but have included in a loop just incase
                pointer = html.indexOf(split[0], pointer);
                boolean location = true;
                int innerPointer = pointer;
                //could do differently to use the max length of an unknown entity eg 30 from method where set the |#| IMPORTANT IF DOESNT WORK
                for (int j = 1; j < split.length; j++) {
                    innerPointer = html.indexOf(split[0], innerPointer);
                    if (innerPointer == -1) {
                        location = false;
                        break;
                    }
                }
                if (location) {
                    //have found it and can set the end index
                    indexEnd = innerPointer + split[split.length - 1].length() - 1;
                    break;
                } else {
                    break; //just added
                }
            }
        }
        return indexStart == -1 || indexEnd == -1 ? "" : html.substring(indexStart, indexEnd);
    }

    public List<String> getListValsFromRule(String htmlFile, Rule rule, boolean testOC, boolean testOpenOnly) {
        //use this when testing and executing but run the head and tail through first so only passing substring of the html
        int indexStart = -1;
        int indexEnd = -1;

        String html = testOC ? extractOC(htmlFile, rule, testOpenOnly) : htmlFile;

        List<String> results = new ArrayList<>();

        boolean hasMore = true;

        int counter = 0;

        while (hasMore) {
            indexStart = -1;
            indexEnd = -1;

            if (!rule.getLeft().contains(MODIFIER_STRING)) {
                indexStart = html.indexOf(rule.getLeft(), counter) + rule.getLeft().length() + 1;
            } else {
                String[] split = rule.getLeft().split(MODIFIER_REGEX);
                int pointer = counter;
                while (true) {
                    pointer = html.indexOf(split[0], pointer);
                    if (pointer == -1) {
                        break;
                    }
                    boolean location = true;
                    int innerPointer = pointer;
                    //could do differently to use the max length of an unknown entity eg 30 from method where set the |#| IMPORTANT IF DOESNT WORK
                    for (int j = 1; j < split.length; j++) {
                        innerPointer = html.indexOf(split[j], innerPointer);
                        if (innerPointer == -1) {
                            location = false;
                            break;
                        }
                    }
                    if (location) {
                        //have found it and can set the end index
                        indexStart = innerPointer + split[split.length - 1].length() + 1;
                        break;
                    } else {
                        break;
                    }
                }
            }

            if (indexStart == -1) {
                break;
            }
            //doesnt need to be nested as is repeated in both
            if (!rule.getRight().contains(MODIFIER_STRING)) {
                indexEnd = html.indexOf(rule.getRight(), indexStart);
                counter = indexEnd;
            } else {
                String[] split = rule.getRight().split(MODIFIER_REGEX);
                int pointer = indexStart; //start here as know it cant be before and dont know the length of the item you are searching for
                while (true) { //should only go through once on this but have included in a loop just incase
                    pointer = html.indexOf(split[0], pointer);
                    boolean location = true;
                    int innerPointer = pointer;
                    //could do differently to use the max length of an unknown entity eg 30 from method where set the |#| IMPORTANT IF DOESNT WORK
                    for (int j = 1; j < split.length; j++) {
                        innerPointer = html.indexOf(split[0], innerPointer);
                        if (innerPointer == -1) {
                            location = false;
                            break;
                        }
                    }
                    if (location) {
                        //have found it and can set the end index
                        indexEnd = innerPointer + split[split.length - 1].length() - 1;
                        counter = indexEnd;
                        break;
                    } else {
                        break; //just added
                    }
                }
            }
            if (indexStart == -1 || indexEnd == -1) {
                break;
            }
            results.add(html.substring(indexStart, indexEnd));
        }
        return results;
    }

    private String extractOC(String html, Rule rule, boolean testOpenOnly) {

        int indexStart = -1;
        int indexEnd = -1;

        indexStart = html.indexOf(rule.getOpen()) + 1;
        if (indexStart == -1) {
            return html;
        }

        if (!testOpenOnly) {
            indexEnd = html.indexOf(rule.getClose(), indexStart);
            if (indexEnd == -1) {
                return html;
            }
        } else {
            indexEnd = html.length() - 1;
        }

        return html.substring(indexStart, indexEnd);
    }
    
    private String extractHT(String html, boolean testHeadOnly, String head, String tail){
        int indexStart = -1;
        int indexEnd = -1;

        if(head.equals("")){
            indexStart = html.indexOf(head) + 1;
            if (indexStart == -1) {
                return html;
                //maybe dont do this? could just set index and carry on
            }
        }
        else indexStart = 0;
        
        if (!testHeadOnly && !tail.equals("")) {
            indexEnd = html.indexOf(tail, indexStart);
            if (indexEnd == -1) {
                return html;
                //maybe dont do this? could just set index and carry on
            }
        } else {
            indexEnd = html.length() - 1;
        }

        return html.substring(indexStart, indexEnd);
        
    }

    // </editor-fold>
    private List<SiteFeatures> getSearchResults(Map<String, ArrayList<String>> domainList) {
        List<SiteFeatures> searchResults = new ArrayList();
        String domain;
        ArrayList<String> urlList;

        Wrapper wInd;
        Wrapper wList;

        SiteFeatures temp;

        Iterator it = domainList.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            domain = pair.getKey().toString();
            wInd = getWrapper(domain, 0);
            if (wInd == null) {
                continue;
            }

            wList = getWrapper(domain, 1);

            urlList = (ArrayList<String>) pair.getValue();

            for (int i = 0; i < urlList.size(); i++) {
                String url = urlList.get(i);
                String html = wpManager.getHTML(url);

                int type = getTypeFromURL(html);
                if (type == 0) {
                    //if ind
                    temp = getSiteFeatures(wInd, html, domain, url);
                    if (temp != null) {
                        searchResults.add(temp);
                    }
                } else if (type == 1) {
                    //if is a list page
                    if (wList != null) {
                        searchResults.addAll(getSiteFeaturesList(wInd, getUrlListFeature(wList, html), domain));
                    }
                } else {
                    //if cant categorise result
                }
            }
        }

        return searchResults;
    }

    private Wrapper getWrapper(String domain, int type) {

        if (type == 0) {
            for (int i = 0; i < individualWrappers.size(); i++) {
                if (individualWrappers.get(i).getDomain().equals(domain)) {
                    return individualWrappers.get(i);
                }
            }
        } else {
            for (int i = 0; i < listWrappers.size(); i++) {
                if (listWrappers.get(i).getDomain().equals(domain)) {
                    return listWrappers.get(i);
                }
            }
        }

        return null;
    }

    private SiteFeatures getSiteFeatures(Wrapper wrapper, String html, String domain, String url) {
        SiteFeatures sf = new SiteFeatures(domain, url);

        for (FeatureEnum feature : FeatureEnum.values()) {
            if (feature == FeatureEnum.URL) {
                continue;
            }
            List<Rule> filteredRules = wrapper.filterRules(feature);

            if (filteredRules != null) {
                //go through all rules saved for each feature
                for (int i = 0; i < filteredRules.size(); i++) {
                    String val = getValFromRule(html, filteredRules.get(i), true, false);
                    //if finds a value or is last rule then input result, else go to next rule
                    if (val != "" || i == filteredRules.size() - 1) {
                        sf.addFeature(feature, val);
                        break;
                    }
                }
            } 
        }
        if (sf.getFeatureMap().size() == 0) {
            return null;
        }
        
        return sf;
    }

    private List<SiteFeatures> getSiteFeaturesList(Wrapper wrapper, List<String> UrlList, String domain) {

        List<SiteFeatures> resultList = new ArrayList<>();
        String html = "";
        SiteFeatures sf;
        String url;

        for (int i = 0; i < UrlList.size(); i++) {
            //set up url
            url = UrlList.get(i);
            if (!url.contains(domain)) {
                if (!url.startsWith("/")) {
                    url = "/" + url;
                }
                url = domain + url;
            }
            html = wpManager.getHTML(url);
            sf = getSiteFeatures(wrapper, html, domain, url);
            if (sf != null) {
                resultList.add(sf);
            }
        }

        return resultList;
    }

    private List<String> getUrlListFeature(Wrapper wrapper, String html) {

        List<String> urlList = new ArrayList<>();

        for (int i = 0; i < wrapper.getRuleList().size(); i++) {
            if (wrapper.getRuleList().get(i).getFeatureName() == FeatureEnum.URL) {
                urlList.addAll(getListValsFromRule(html, wrapper.getRuleList().get(i), true, true));
            }
        }

        if (wrapper.getRuleList().size() > 1) {
            //remove duplicates
            Set<String> hs = new HashSet<>();
            hs.addAll(urlList);
            urlList.clear();
            urlList.addAll(hs);
        }

        return urlList;
    }

    private int getTypeFromURL(String html) {
        int type = -1;

        return type;
    }
}
