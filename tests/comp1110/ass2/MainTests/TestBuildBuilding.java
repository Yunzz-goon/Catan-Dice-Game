//package comp1110.ass2.MainTests;
//
//import comp1110.ass2.Main.BuildBuilding;
//import org.junit.jupiter.api.Test;
//
//
//import java.util.Arrays;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class TestBuildBuilding {
//
//    private static String errorPrefixBuildingType(int i, String build_type) {
//        return "BuildBuilding.BuildBuilding(" + i + ", " + build_type + ")";
//    }
//
//    private static String errorPrefixOrder(){
//        return "";
//    }
//
//    private static String errorPrefixResource(){
//        return "";
//    }
//
//    private static String errorPrefixBoard(){
//        return "";
//    }
//
//    private static int[] stuctureNeedResource(String structure){
//        return null;
//    }
//
//    @Test
//    public static void checkDefaultStatus(){
//        BuildBuilding buildBuilding = new BuildBuilding();
//        // test initialization status of roads
//        for (int i = 0; i < 16; i++){
//            String errorprefix = errorPrefixBuildingType(i, "Road");
//            assertEquals(false, buildBuilding.roads.get(i).getStatus(), errorprefix);
//        }
//
//        //TODO do the similar thing for cities, settlements, knights
//    }
//
//    @Test
//    public static void checkOrder(){
//
//    }
//
//    @Test
////    public static void testResourceState(String structure, int[] resource_state) {
////        String errorprefix = errorPrefixResource();
////        BuildBuilding buildbuildings = new BuildBuilding();
////        int[] resource_state_copy = Arrays.copyOf(resource_state, 6);
////        buildbuildings.buildBuilding(structure, resource_state_copy);
////        int[] resource_required = stuctureNeedResource(structure);
////        int[] shouldBe = new int[6];
////        for (int i = 0; i < 6; i++) {
////            shouldBe[i] = resource_state_copy[i] + resource_required[i];
////        }
////        assertEquals(shouldBe, resource_state, errorprefix);
////    }
//
//    @Test
//    public static void testBoardState(String structure, String board_state, int[] resource_state) {
//        String errorprefix = errorPrefixBoard();
//        BuildBuilding buildbuildings = new BuildBuilding();
//        String board_state_copy = board_state;
//        buildbuildings.buildBuilding(structure, resource_state);
//        String shouldBe = board_state_copy + "," + structure;
//        assertEquals(shouldBe, board_state, errorprefix);
//
//    }
//
//    public static void main(String[] args) {
//        TestBuildBuilding testbuildings = new TestBuildBuilding();
//        System.out.println("Testing");
//        testbuildings.checkDefaultStatus();
//        testbuildings.checkOrder();
//        String structure = null;
//        int[] resource_state = null;
//        String board_state = null;
//        testbuildings.testResourceState(structure, resource_state);
//        testbuildings.testBoardState(structure, board_state, resource_state);
//        System.out.println("Test done");
//    }
//}
