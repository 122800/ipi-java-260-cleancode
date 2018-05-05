package main;

public class mauvaisCode() {

    public void commentaires_bad(OutputStream outputStream, List<ClientDTO> clients) {

        XSLXFileGenerator<ClientDTO> excelWriter = new XSLXFileGenerator<>();

        // maybe get a list of the names of each of the victims?
        excelWriter.addColumn("Noms des victimes",
            (client) -> client.getVictims().stream()// get each victim
                .map((victime) -> victime.getNom())// get name from each victim
                .reduce("", (a, b) -> // concatenate list of names
                    a
                    + (a.length() > 0 ? ", " : "")// add ', ' after first name in the list
                    + b)
            );
    }

    public void commentaires_better(OutputStream outputStream, List<ClientDTO> clients) {

        XSLXFileGenerator<ClientDTO> excelWriter = new XSLXFileGenerator<>();

        excelWriter.addColumn("Noms des victimes", (client) -> {
            List<String> listeDesNoms = client.getVictims().stream().map(v -> v.getNom()).collect(toList());
            String listeFormateeDesNoms = String.join(",", listeDesNoms);
            return listeFormateeDesNoms;
        });

        // Maybe this code is better?

    }

}