package org.code.exercise;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class DecodeMsg {
    static void main() throws IOException, InterruptedException {
        String url = "https://docs.google.com/document/d/e/2PACX-1vSvM5gDlNvt7npYHhp_XfsJvuntUhq184By5xO_pA4b_gCWeXb6dM6ZxwN8rE6S4ghUsCj2VKR21oEP/pub\n" +
                "\n";
        DecodeMsg.decodeMsg(url);
    }

    public static void decodeMsg(String gDocUrl) throws IOException, InterruptedException {
        //jsoup.org
        Document doc = Jsoup.connect(gDocUrl).get();
        Elements rows = doc.select("table tr");
        //For debug only
        /* System.out.println("Number of rows: " + rows.size()); */
        Map<String,Character> grid = new HashMap<>();
        int max_X = 0;
        int max_Y = 0;

        // Parse the HTML table and store each character at its x,y coordinates
        for(int i=1;i<rows.size();i++) {
            Elements cells = rows.get(i).select("td");
            if(cells.size() >= 3) {
                try {
                    int x = Integer.parseInt(cells.get(0).text().trim());
                    char ch = cells.get(1).text().charAt(0);
                    int y = Integer.parseInt(cells.get(2).text().trim());

                    grid.put(x + "," + y,ch);
                    max_X = Math.max(max_X,x);
                    max_Y = Math.max(max_Y,y);
                } catch (Exception e) {}
            }
        }

        // Print each row from left to right as x increase.
        System.out.println("|");
        for(int y = max_Y; y>=0;y--) {
            // Starts at zero and works toward the right because x increases from left to right.
            for(int x=0;x<=max_X;x++) {
                System.out.print(grid.getOrDefault(x + "," + y, ' '));
            }
            System.out.println();
        }
        System.out.println("|");
    }
}
