public class PlainStudentPractice {
    static class Card {
        private String fullName;
        Card(String fullName) { this.fullName = fullName; }
        String getFullName() { return fullName; }
    }
    public static void main(String[] args) {
        Card card = new Card("Amina & Tunde");
        String xml = "<student><full_name>Amina &amp; Tunde</full_name></student>";
        System.out.println("Object getter: " + card.getFullName());
        System.out.println("XML text: " + xml);
        System.out.println("Same object as another new Card: " + (card == new Card("Amina & Tunde")));
    }
}
