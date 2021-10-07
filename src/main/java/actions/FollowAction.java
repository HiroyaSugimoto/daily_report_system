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
import services.EmployeeService;
import services.FollowService;
import services.ReportService;

public class FollowAction extends ActionBase {

    private FollowService service;
    private EmployeeService es;
    private ReportService rs;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new FollowService();
        es = new EmployeeService();
        rs = new ReportService();

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
     * フォローしている特定の従業員のレポート一覧を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void followeeIndex() throws ServletException, IOException {

        //idを条件にフォロー対象者の従業員情報を取得
        int followeeEmpId = Integer.parseInt(getRequestParam(AttributeConst.EMP_ID));
        EmployeeView followeeEmp = es.findOne(followeeEmpId);

        //フォロー対象の従業員が作成した日報データを指定されたページ数の一覧画面に表示する分取得する
        int page = getPage();
        List<ReportView> reports = rs.getMinePerPage(followeeEmp, page);

        //フォロー対象の従業員が作成した日報データの件数を取得
        long followeeRepCount = rs.countAllMine(followeeEmp);

        putRequestScope(AttributeConst.REPORTS, reports); //取得した日報データ
        putRequestScope(AttributeConst.REP_COUNT, followeeRepCount); //全ての日報データの件数
        putRequestScope(AttributeConst.PAGE, page); //ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数
        putRequestScope(AttributeConst.EMPLOYEE, followeeEmp); //取得した従業員情報

        //セッションからログイン中の従業員情報を取得
        EmployeeView followerEmp = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

        //ログイン中の従業員が対象の従業員をフォローしているか確認
        long followCheck = service.alreadyFollowCheck(followerEmp, followeeEmp);

        if(followCheck == 1) {
            //フォローしていたら一覧画面を表示
            forward(ForwardConst.FW_FOL_FOLLOWEE_INDEX);
        } else {
            //フォローしていなかったらエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);
            return;
        }

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
