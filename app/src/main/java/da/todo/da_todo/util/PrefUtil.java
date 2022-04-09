package da.todo.da_todo.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import da.todo.da_todo.Controllers.TimerActivity;

public class PrefUtil
{
    private static final String PREVIOUS_TIMER_LENGTH_SECONDS_ID = "com.example.da_todo.previous_timer_length";

    public static int getTimerLength(Context context)
    {
        return 5;
    }

    public static Long getPreviousTimerLengthSeconds(Context context)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getLong(PREVIOUS_TIMER_LENGTH_SECONDS_ID, 0);
    }

    public static void setPreviousTimerLengthSeconds(Long seconds, Context context)
    {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putLong(PREVIOUS_TIMER_LENGTH_SECONDS_ID, seconds);
        editor.apply();
    }

    private static final String TIMER_STATE_ID = "com.example.da_todo.timer_state";

    public static TimerActivity.TimerState getTimerState(Context context)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        int ordinal = preferences.getInt(TIMER_STATE_ID, 0);
        return TimerActivity.TimerState.values()[ordinal];
    }

    public static void setTimerState(TimerActivity.TimerState state, Context context)
    {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        int ordinal = state.ordinal();
        editor.putInt(TIMER_STATE_ID, ordinal);
        editor.apply();
    }

    private static final String SECONDS_REMAINING_ID = "com.example.da_todo.seconds_remaining";

    public static Long getSecondsRemaining(Context context)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getLong(SECONDS_REMAINING_ID, 0);
    }

    public static void setSecondsRemaining(Long seconds, Context context)
    {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putLong(SECONDS_REMAINING_ID, seconds);
        editor.apply();
    }
}
