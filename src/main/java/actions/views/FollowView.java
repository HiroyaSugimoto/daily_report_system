package actions.views;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * フォロー情報について画面の入力値・出力値を扱うViewモデル
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FollowView {

    /**
     * id
     */
    private Integer id;

    /**
     * フォローされている従業員
     */
    private EmployeeView followee;

    /**
     * フォローしている従業員
     */
    private EmployeeView follower;

    /**
     * フォローした日時
     */
     private LocalDateTime followedAt;

}
