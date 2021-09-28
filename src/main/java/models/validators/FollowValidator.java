package models.validators;

import java.util.ArrayList;
import java.util.List;

import actions.views.EmployeeView;
import actions.views.FollowView;
import constants.MessageConst;

/**
 * フォローインスタンスに設定されている値のバリデーションを行うクラス
 */
public class FollowValidator {

    /**
     * フォローインスタンスの各項目についてバリデーションを行う
     * @param fv フォローインスタンス
     * @return エラーのリスト
     */
    public static List<String> validate(FollowView fv){
        List<String> errors = new ArrayList<String>();

        //followeeのチェック
        String followeeError = validateFollowee(fv.getFollowee());
        if(!followeeError.equals("")) {
            errors.add(followeeError);
        }

        //followerのチェック
        String followerError = validateFollower(fv.getFollower());
        if(!followerError.equals("")) {
            errors.add(followerError);
        }

        return errors;
    }

    /**
     * followeeに値があるかをチェックし、値がなければエラーメッセージを返却
     * @param employeeView フォローを行う従業員
     * @return エラーメッセージ
     */
    private static String validateFollowee(EmployeeView employeeView) {
        if(employeeView == null) {
            return MessageConst.E_NOFOLLOW.getMessage();
        }
        //値が設定されている場合、空文字を返却
        return "";
    }

    /**
     * followerに値があるかをチェックし、値がなければエラーメッセージを返却
     * @param employeeView フォローを行う従業員
     * @return エラーメッセージ
     */
    private static String validateFollower(EmployeeView employeeView) {
        if(employeeView == null) {
            return MessageConst.E_NOFOLLOW.getMessage();
        }
        //値が設定されている場合、空文字を返却
        return "";
    }

}
