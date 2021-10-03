package actions;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * フォローに関する処理を行うActionクラス
 */

import javax.servlet.ServletException;

import actions.views.EmployeeView;
import actions.views.FollowView;
import actions.views.ReportView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import services.FollowService;
import services.ReportService;

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
        List<FollowView> followee = service.getMyFolloweePerPage(loginEmployee, page);

        //ログイン中の従業員がフォロー中の従業員の件数を取得
        long myFolloweeCount = service.countMyFollowee(loginEmployee);

        putRequestScope(AttributeConst.FOLLOWEE, followee); //取得したフォロー情報
        putRequestScope(AttributeConst.FOLLOWEE_COUNT, myFolloweeCount); //ログイン中の従業員がフォローしている数
        putRequestScope(AttributeConst.PAGE, page); //ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数

        //フォローの一覧画面を表示
        forward(ForwardConst.FW_FOL_INDEX);
    }

    /**
     * 新規フォローの登録を行う
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {

      //フォローした日時を設定（DBテーブル用）
      LocalDateTime followedAt = LocalDateTime.now();

      //セッションからログイン中の従業員情報を取得
      EmployeeView followerEmp = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

      //表示中のレポートの"id"を取得
      String commId = getSessionScope(AttributeConst.REP_ID);
      int repId = Integer.parseInt(commId);

      //リクエストURL"id"の値から、レポート情報を取得
      ReportView followeeEmpRep = new ReportView();
      ReportService rs = new ReportService();
      followeeEmpRep = rs.findOne(repId);

      //取得したレポートデータのEmployee_idを使用してフォローする従業員データを取得
      EmployeeView followeeEmp = new EmployeeView();
      followeeEmp = followeeEmpRep.getEmployee();

      //フォロー情報を登録
      FollowView fv = new FollowView(
              null,
              followeeEmp,
              followerEmp,
              followedAt);

      service.create(fv);

      //セッションにフラッシュメッセージが登録されている場合はリクエストスコープに設定する
      String flush = getSessionScope(AttributeConst.FLUSH);
      if(flush != null) {
          putRequestScope(AttributeConst.FLUSH, getSessionScope(AttributeConst.FLUSH));
          removeSessionScope(AttributeConst.FLUSH);
      }

      redirectFollow(ForwardConst.ACT_REP, ForwardConst.CMD_SHOW, commId);

    }

    /**
     * フォローを解除する
     * @throws ServletException
     * @throws IOException
     */
    public void destroy() throws ServletException, IOException {

      //セッションからログイン中の従業員情報を取得
        EmployeeView followerEmp = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

        //表示中のレポートの"id"を取得
        String commId = getSessionScope(AttributeConst.REP_ID);
        int repId = Integer.parseInt(commId);

        //リクエストURL"id"の値から、レポート情報を取得
        ReportView followeeEmpRep = new ReportView();
        ReportService rs = new ReportService();
        followeeEmpRep = rs.findOne(repId);

        //取得したレポートデータのEmployee_idを使用してフォローする従業員データを取得
        EmployeeView followeeEmp = new EmployeeView();
        followeeEmp = followeeEmpRep.getEmployee();

        //フォローしている従業員とフォローされてる従業員の情報を元に、1件のフォローデータidを取得
        int followId = service.getOneFolloweeId(followerEmp, followeeEmp);

        //フォローのテーブルからデータを1件削除
        service.deleteFollowData(followId);

        //レポート詳細ページを再表示
        redirectFollow(ForwardConst.ACT_REP, ForwardConst.CMD_SHOW, commId);
    }
}
