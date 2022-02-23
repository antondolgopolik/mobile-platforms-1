package by.bsuir.mobileplatforms1.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import by.bsuir.mobileplatforms1.ApplicationState;
import by.bsuir.mobileplatforms1.R;
import by.bsuir.mobileplatforms1.entity.Result;
import by.bsuir.mobileplatforms1.service.ResultService;
import by.bsuir.mobileplatforms1.service.UserService;

public class LastResultWidget extends AppWidgetProvider {



    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                 int appWidgetId) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.last_result_widget);
        views.setTextViewText(R.id.appwidget_text, getLastResultText(context));
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private String getLastResultText(Context context) {
        String username = ApplicationState.getUsername();
        if (username != null) {
            ResultService resultService = new ResultService(context);
            Result lastResult = resultService.getLastResultForUser(username);
            String text = "Your last result: ";
            if (lastResult != null) {
                return text + lastResult.getPoints();
            } else {
                return text + "no";
            }
        } else {
            return "You're not logged in";
        }
    }
}