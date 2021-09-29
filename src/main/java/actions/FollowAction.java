package actions;

import java.io.IOException;
import java.util.List;

/**
 * フォローに関する処理を行うActionクラス
 */

import javax.servlet.ServletException;

import actions.views.EmployeeView;
import actions.views.FollowView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import services.FollowService;

public class FollowAction extends ActionBase {

    private FollowService service;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new FollowService();

        //メソッドを実行
        invoke();
        service.close();
    }

    /**
     * フォローしている従業員の一覧画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void index() throws ServletException, IOException {

        //セッションからログイン中の従業員情報を取得
        EmployeeView loginEmployee = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

        //指定されたページ数の一覧画面に表示するフォローデータを取得
        int page = getPage();
        List<FollowView> followee = service.getMineFolloweePerPage(loginEmployee, page);

        //ログイン中の従業員がフォロー中の従業員の件数を取得
        long myFolloweeCount = service.countMineFollow(loginEmployee);

        putRequestScope(AttributeConst.FOLLOWEE, followee); //取得したフォロー情報
        putRequestScope(AttributeConst.FOLLOWEE_COUNT, myFolloweeCount); //ログイン中の従業員がフォローしている数
        putRequestScope(AttributeConst.PAGE, page); //ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数

        //フォローの一覧画面を表示
        forward(ForwardConst.FW_FOL_INDEX);

    }

}
