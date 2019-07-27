package raslan.learn.islam.util;

 public final class LessonListenerHelper {

    private static LessonListener lessonListener = null;

    public LessonListenerHelper(LessonListener lessonListener){
        LessonListenerHelper.lessonListener = lessonListener;
    }

    public static LessonListener lessonListener(){return lessonListener;}
}
