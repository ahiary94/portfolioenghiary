package com.example.abeer.mysecretportfolio.plugins.positivequotes;

import com.example.abeer.mysecretportfolio.R;

import java.util.ArrayList;

public class PositiveQuotesList {

    private ArrayList<Integer> color = new ArrayList<>();

    private ArrayList<String> quote = new ArrayList<>();


    public ArrayList<Integer> returnColor() {
        color.add(R.color.blue);
        color.add(R.color.orange);
        color.add(R.color.purple);
        color.add(R.color.white);
        color.add(R.color.yellow);
        color.add(R.color.red);
        color.add(R.color.greenTwo);
        color.add(R.color.pink);
        color.add(R.color.green);
        color.add(R.color.orangeTwo);

        return color;

    }

    public ArrayList<String> returnQuotes() {
        quote.add("Never Give UP");
        quote.add("It's a bad DAY not a bad LIFE");
        quote.add("YOUR ONLY LIMIT IS YOUR MIND");
        quote.add("STOP letting people who do so little for you control so much of your mind"
                + ", feelings and emotions ");
        quote.add("you can't live a positive life with a negative mind");
        quote.add("\"Don't be pushed around by the fears in your mind. Be led by the dreams in your heart.\" \n" +
                "― Roy T. Bennett, The Light in the Heart");
        quote.add("REMEMBER, most of your stress comes from the way you respond, not the way life is"
                + ", adjust your attitude and all the extra stress is gone");
        quote.add("YOU ARE WHAT YOU DO ... NOT WHAT YOU SAY ... YOU WILL DO");
        quote.add("DON'T let the behaviour of others destroy your inner peace");
        quote.add("\"Believe in yourself! Have faith in your abilities! Without a humble but reasonable confidence in your own powers you cannot be successful or happy.\"" +
                "—Norman Vincent Peale");
        return quote;
    }


}
