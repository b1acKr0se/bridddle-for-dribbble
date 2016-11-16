package io.b1ackr0se.bridddle.ui.search.converter;


import android.text.TextUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.b1ackr0se.bridddle.data.model.Images;
import io.b1ackr0se.bridddle.data.model.Shot;
import io.b1ackr0se.bridddle.data.model.User;
import okhttp3.ResponseBody;

/**
 * Most of the code in this class is from nickbutcher's plaid app
 *
 * https://github.com/nickbutcher/plaid/blob/2048e69ef233a816f5d53b3c23c56c7262c0168f/app/src/main/java/io/plaidapp/data/api/dribbble/DribbbleSearchConverter.java
 */
public class DribbbleSearchConverter {

    private static final String HOST = "https://dribbble.com";

    private static final Pattern PATTERN_PLAYER_ID =
            Pattern.compile("users/(\\d+?)/", Pattern.DOTALL);

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMMM d, yyyy");

    public static List<Shot> parseShots(ResponseBody responseBody) throws IOException {
        List<Shot> shots = new ArrayList<>();
        final Elements shotElements =
                Jsoup.parse(responseBody.string(), HOST).select("li[id^=screenshot]");
        for (Element element : shotElements) {
            final Shot shot = parseShot(element, DATE_FORMAT);
            if (shot != null) {
                shots.add(shot);
            }
        }
        return shots;
    }

    private static Shot parseShot(Element element, SimpleDateFormat dateFormat) {
        final Element descriptionBlock = element.select("a.dribbble-over").first();
        // API responses wrap description in a <p> tag. Do the same for consistent display.
        String description = descriptionBlock.select("span.comment").text().trim();
        if (!TextUtils.isEmpty(description)) {
            description = "<p>" + description + "</p>";
        }
        String imgUrl = element.select("img").first().attr("src");
        if (imgUrl.contains("_teaser.")) {
            imgUrl = imgUrl.replace("_teaser.", ".");
        }
        Date createdAt = null;
        try {
            createdAt = dateFormat.parse(descriptionBlock.select("em.timestamp").first().text());
        } catch (ParseException e) {
        }

        Shot shot = new Shot();
        shot.setId(Integer.parseInt(element.id().replace("screenshot-", "")));
        shot.setHtmlUrl(HOST + element.select("a.dribbble-link").first().attr("href"));
        shot.setTitle(descriptionBlock.select("strong").first().text());
        shot.setDescription(description);
        shot.setImages(new Images(null, imgUrl, null));
        shot.setAnimated(element.select("div.gif-indicator").first() != null);
        shot.setCreatedAt(createdAt);
        shot.setLikesCount(Integer.parseInt(element.select("li.fav").first().child(0).text()
                .replaceAll(",", "")));
        shot.setCommentsCount(Integer.parseInt(element.select("li.cmnt").first().child(0).text
                        ().replaceAll(",", "")));
        shot.setViewsCount(Integer.parseInt(element.select("li.views").first().child(0)
                        .text().replaceAll(",", "")));
        shot.setUser(parsePlayer(element.select("h2").first()));
        return shot;
    }

    private static User parsePlayer(Element element) {
        final Element userBlock = element.select("a.url").first();
        String avatarUrl = userBlock.select("img.photo").first().attr("src");
        if (avatarUrl.contains("/mini/")) {
            avatarUrl = avatarUrl.replace("/mini/", "/normal/");
        }
        final Matcher matchId = PATTERN_PLAYER_ID.matcher(avatarUrl);
        Integer id = -1;
        if (matchId.find() && matchId.groupCount() == 1) {
            id = Integer.parseInt(matchId.group(1));
        }
        final String slashUsername = userBlock.attr("href");
        final String username =
                TextUtils.isEmpty(slashUsername) ? null : slashUsername.substring(1);
        User user = new User();
        user.setId(id);
        user.setName(userBlock.text());
        user.setUsername(username);
        user.setHtmlUrl(HOST + slashUsername);
        user.setAvatarUrl(avatarUrl);
        user.setPro(element.select("span.badge-pro").size() > 0);
        return user;
    }

}
