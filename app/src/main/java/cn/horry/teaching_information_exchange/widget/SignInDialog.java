package cn.horry.teaching_information_exchange.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import cn.horry.teaching_information_exchange.R;

/**
 * Created by Myy on 2016/2/7.
 */
public class SignInDialog extends Dialog {
    public SignInDialog(Context context) {
        super(context);
    }

    public SignInDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected SignInDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
    public static class Builder {
        private Context context;
        private String title;
        private String R1;
        private String R2;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;
        private DialogInterface.OnClickListener positiveButtonClickListener;
        private DialogInterface.OnClickListener negativeButtonClickListener;
        private OnOkListener onOkListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setR1(String R1) {
            this.R1 = R1;
            return this;
        }

        /**
         * Set the Dialog message from resource
         *
         * @param R1
         * @return
         */
        public Builder setR1(int R1) {
            this.R1 = (String) context.getText(R1);
            return this;
        }

        public Builder setR2(String R2) {
            this.R2 = R2;
            return this;
        }

        /**
         * Set the Dialog message from resource
         *
         * @param R2
         * @return
         */
        public Builder setR2(int R2) {
            this.R2 = (String) context.getText(R2);
            return this;
        }
        /**
         * Set the Dialog title from resource
         *
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * Set the Dialog title from String
         *
         * @param title
         * @return
         */

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

//        /**
//         * Set the positive button resource and it's listener
//         *
//         * @param positiveButtonText
//         * @return
//         */
//        public Builder setPositiveButton(int positiveButtonText,
//                                         DialogInterface.OnClickListener listener) {
//            this.positiveButtonText = (String) context
//                    .getText(positiveButtonText);
//            this.positiveButtonClickListener = listener;
//            return this;
//        }
//
//        public Builder setPositiveButton(String positiveButtonText,
//                                         DialogInterface.OnClickListener listener) {
//            this.positiveButtonText = positiveButtonText;
//            this.positiveButtonClickListener = listener;
//            return this;
//        }

        public Builder setNegativeButton(int negativeButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.negativeButtonText = (String) context
                    .getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }
        public Builder setOnOkListener(OnOkListener onOkListener){
            this.onOkListener=onOkListener;
            return this;
        }
        public SignInDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final SignInDialog dialog = new SignInDialog(context,R.style.Dialog);
            final View layout = inflater.inflate(R.layout.my_dialog, null);
            dialog.addContentView(layout, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            // set the dialog title
            ((TextView) layout.findViewById(R.id.title)).setText(title);
            // set the confirm button
//            if (onOkListener != null) {
//                ((Button) layout.findViewById(R.id.save))
//                        .setText(positiveButtonText);
                if (onOkListener != null) {
                     layout.findViewById(R.id.save)
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    onOkListener.getDialogRadioButton(dialog,(RadioButton) layout.findViewById(((RadioGroup) layout.findViewById(R.id.content)).getCheckedRadioButtonId()));
                                }
                            });
                }
//            } else {
//                // if no confirm button just set the visibility to GONE
//                layout.findViewById(R.id.save).setVisibility(
//                        View.GONE);
//            }
            // set the cancel button
            if (negativeButtonText != null) {
                ((Button) layout.findViewById(R.id.cancel))
                        .setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.cancel))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    negativeButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_NEGATIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.cancel).setVisibility(
                        View.GONE);
            }
            // set the content message
            if (R1 != null &&R2 !=null) {
                ((TextView) layout.findViewById(R.id.sign_in)).setText(R1);
                ((TextView) layout.findViewById(R.id.stop_sign_in)).setText(R2);
            } else if (contentView != null) {
                // if no message set
                // add the contentView to the dialog body
                ((LinearLayout) layout.findViewById(R.id.content))
                        .removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.content))
                        .addView(contentView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
            }
            dialog.setContentView(layout);
            return dialog;
        }
        public interface OnOkListener{
            void getDialogRadioButton(DialogInterface dialog,RadioButton radioButton);
        }
    }
}
