package br.com.lukinhasssss.proposta.models.enums

enum class ProposalStatus {
    NAO_ELEGIVEL, ELEGIVEL;

    companion object {
        fun convert(solicitationResult: String): ProposalStatus? {
            val mapper: HashMap<String, ProposalStatus> = hashMapOf()

            mapper["COM_RESTRICAO"] = NAO_ELEGIVEL
            mapper["SEM_RESTRICAO"] = ELEGIVEL

            return mapper[solicitationResult]
        }
    }
}