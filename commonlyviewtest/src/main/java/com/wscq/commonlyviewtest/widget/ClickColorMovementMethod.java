package com.wscq.commonlyviewtest.widget;

import android.graphics.Color;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.widget.TextView;

import com.wscq.commonlyviewtest.R;

/**
 * @author 胡文勇
 * @email wenyong.hu@139.com
 * @createTime 2018/6/28
 * @describe 点击时候, 触发背景色改变的类
 */
public class ClickColorMovementMethod extends LinkMovementMethod {
    private static ClickColorMovementMethod sInstance;

    public static ClickColorMovementMethod getInstance() {
        if (sInstance == null)
            sInstance = new ClickColorMovementMethod();

        return sInstance;
    }

    @Override
    public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {

        int action = event.getAction();

        if (action == MotionEvent.ACTION_UP ||
                action == MotionEvent.ACTION_DOWN ||
                action == MotionEvent.ACTION_MOVE) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            x -= widget.getTotalPaddingLeft();
            y -= widget.getTotalPaddingTop();

            x += widget.getScrollX();
            y += widget.getScrollY();

            Layout layout = widget.getLayout();
            int line = layout.getLineForVertical(y);
            int off = layout.getOffsetForHorizontal(line, x);

            ClickableSpan[] link = buffer.getSpans(off, off, ClickableSpan.class);

            if (link.length != 0) {
                if (action == MotionEvent.ACTION_UP) {
                    link[0].onClick(widget);

                    buffer.setSpan(new BackgroundColorSpan(Color.TRANSPARENT),
                            buffer.getSpanStart(link[0]), buffer.getSpanEnd(link[0]),
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                    Selection.removeSelection(buffer);

                } else if (action == MotionEvent.ACTION_DOWN) {
                    buffer.setSpan(new BackgroundColorSpan(widget.getContext().getResources().getColor(R.color.text_click_background)),
                            buffer.getSpanStart(link[0]), buffer.getSpanEnd(link[0]),
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                    Selection.setSelection(buffer,
                            buffer.getSpanStart(link[0]),
                            buffer.getSpanEnd(link[0]));
                } else if (action == MotionEvent.ACTION_MOVE) {

                    buffer.setSpan(new BackgroundColorSpan(Color.TRANSPARENT),
                            buffer.getSpanStart(link[0]), buffer.getSpanEnd(link[0]),
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                    Selection.removeSelection(buffer);
                }

                return true;
            } else {
                Selection.removeSelection(buffer);
            }
        }
//        return super.onTouchEvent(widget, buffer, event);
        return false;
    }
}
