package com.powerge.wise.powerge.operationProjo.net.bean;

/**
 * Created by ycs on 2017/2/14.
 */

public class Message {

    /**
     * msg_id : uu72026148704232919911
     * display_type : notification
     * alias :
     * random_min : 0
     * body : {"title":"anyh","ticker":"anyh","text":"anyh","after_open":"go_app","play_vibrate":"true","play_sound":"true","play_lights":"true"}
     */

    private String msg_id;
    private String display_type;
    private String alias;
    private int random_min;
    /**
     * title : anyh
     * ticker : anyh
     * text : anyh
     * after_open : go_app
     * play_vibrate : true
     * play_sound : true
     * play_lights : true
     */

    private BodyBean body;

    public String getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }

    public String getDisplay_type() {
        return display_type;
    }

    public void setDisplay_type(String display_type) {
        this.display_type = display_type;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getRandom_min() {
        return random_min;
    }

    public void setRandom_min(int random_min) {
        this.random_min = random_min;
    }

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public static class BodyBean {
        private String title;
        private String ticker;
        private String text;
        private String after_open;
        private String play_vibrate;
        private String play_sound;
        private String play_lights;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTicker() {
            return ticker;
        }

        public void setTicker(String ticker) {
            this.ticker = ticker;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getAfter_open() {
            return after_open;
        }

        public void setAfter_open(String after_open) {
            this.after_open = after_open;
        }

        public String getPlay_vibrate() {
            return play_vibrate;
        }

        public void setPlay_vibrate(String play_vibrate) {
            this.play_vibrate = play_vibrate;
        }

        public String getPlay_sound() {
            return play_sound;
        }

        public void setPlay_sound(String play_sound) {
            this.play_sound = play_sound;
        }

        public String getPlay_lights() {
            return play_lights;
        }

        public void setPlay_lights(String play_lights) {
            this.play_lights = play_lights;
        }
    }
}
